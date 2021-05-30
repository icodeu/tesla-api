/*
 * Copyright (C) 2021 Baidu, Inc. All Rights Reserved.
 */
package com.icodeyou.teslaapi.lib;

import java.io.IOException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.RandomStringUtils;

import com.alibaba.fastjson.JSONObject;
import com.icodeyou.teslaapi.util.OkHttpUtil;
import com.icodeyou.teslaapi.util.EnsureUtil;

import okhttp3.Response;

public class AuthUtil {

    /**
     * Login tesla account with email and password. Will return the access token if login successful.
     * @param email
     * @param password
     * @return access token
     */
    public static String login(String email, String password) {
        // Step 1 Generate code verifier and code_challenge
        String codeVerifier = RandomStringUtils.randomAlphanumeric(86);
        String codeChallenge = null;
        try {
            codeChallenge = Base64.encodeBase64URLSafeString(
                    Hex.encodeHexString(MessageDigest.getInstance("SHA-256").digest(codeVerifier.getBytes("UTF-8")))
                            .getBytes("UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        EnsureUtil.notEmpty(codeChallenge, "Error when generate code challenge");

        // Step 2 Get the login html
        Map<String, String> urlParams = new HashMap<>();
        urlParams.put("client_id", "ownerapi");
        urlParams.put("code_challenge", codeChallenge);
        urlParams.put("code_challenge_method", "S256");
        urlParams.put("redirect_uri", "https://auth.tesla.com/void/callback");
        urlParams.put("response_type", "code");
        urlParams.put("scope", "openid email offline_access");
        urlParams.put("state", RandomStringUtils.randomAlphanumeric(20));
        urlParams.put("login_hint", email);
        Response loginResponse = OkHttpUtil.getInstance().get("https://auth.tesla.com/oauth2/v3/authorize", urlParams);
        String loginResponseHtml = null;
        StringBuilder cookiesBuilder = new StringBuilder();
        try {
            loginResponseHtml = loginResponse.body().string();
            List<String> setCookies = loginResponse.headers("set-cookie");
            for (String setCookie : setCookies) {
                String[] cookieElement = setCookie.split("=");
                cookiesBuilder.append(cookieElement[0]).append("=").append(cookieElement[1].split(";")[0]).append(";");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        EnsureUtil.notEmpty(loginResponseHtml, "Error when get login html");

        List<String> inputNameList = regMatch(loginResponseHtml, "input type", "name");
        List<String> inputValueList = regMatch(loginResponseHtml, "input type", "value");
        Map<String, String> inputMap = new HashMap<>();
        for (int i = 0; i < inputNameList.size(); i++) {
            inputMap.put(inputNameList.get(i), inputValueList.get(i));
        }

        // Step 3 Login with email and password
        Map<String, String> postParams = new HashMap<>();
        postParams.put("identity", email);
        postParams.put("credential", password);
        inputMap.entrySet().forEach(entry -> postParams.put(entry.getKey(), entry.getValue()));
        Map<String, String> cookieHeader = new HashMap<String, String>() {{
            put("Cookie", cookiesBuilder.toString());
        }};
        loginResponse = OkHttpUtil.getInstance()
                .postForm("https://auth.tesla.cn/oauth2/v3/authorize", urlParams, postParams, cookieHeader);
        // 302 Redirect, so get the code from prior response
        String code = loginResponse.priorResponse().request().url().queryParameter("code");
        EnsureUtil.notEmpty(code, "Error when get code");

        // Step 4 Get bear access token by code above
        JSONObject tokenParams = new JSONObject();
        tokenParams.put("grant_type", "authorization_code");
        tokenParams.put("client_id", "ownerapi");
        tokenParams.put("code", code);
        tokenParams.put("code_verifier", codeVerifier);
        tokenParams.put("redirect_uri", "https://auth.tesla.com/void/callback");
        Response bearTokenResponse =
                OkHttpUtil.getInstance().postJson("https://auth.tesla.cn/oauth2/v3/token", tokenParams, cookieHeader);
        String bearAccessToken = null;
        try {
            bearAccessToken = JSONObject.parseObject(bearTokenResponse.body().string()).getString("access_token");
        } catch (IOException e) {
            e.printStackTrace();
        }
        EnsureUtil.notEmpty(bearAccessToken, "Error when get bear access token");

        // Step 5 Exchange the bear access token to real access token
        JSONObject realTokenParams = new JSONObject();
        realTokenParams.put("grant_type", "urn:ietf:params:oauth:grant-type:jwt-bearer");
        realTokenParams.put("client_id", "81527cff06843c8634fdc09e8ac0abefb46ac849f38fe1e431c2ef2106796384");
        Map<String, String> header = new HashMap<>();
        header.put("authorization", "Bearer " + bearAccessToken);
        Response realTokenResponse = OkHttpUtil.getInstance()
                .postJson("https://owner-api.teslamotors.com/oauth/token", realTokenParams, header);
        String realAccessToken = null;
        try {
            realAccessToken = JSONObject.parseObject(realTokenResponse.body().string()).getString(
                    "access_token");
        } catch (IOException e) {
            e.printStackTrace();
        }
        EnsureUtil.notEmpty(realAccessToken, "Error when get real access token");
        return realAccessToken;
    }

    private static List<String> regMatch(String source, String element, String attr) {
        List<String> result = new ArrayList<>();
        String reg = "<" + element + "[^<>]*?\\s" + attr + "=['\"]?(.*?)['\"]?(\\s.*?)?>";
        Matcher m = Pattern.compile(reg).matcher(source);
        while (m.find()) {
            String r = m.group(1);
            result.add(r);
        }
        return result;
    }

}
