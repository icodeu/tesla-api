/*
 * Copyright (C) 2021 Baidu, Inc. All Rights Reserved.
 */
package com.icodeyou.teslaapi.util;

import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.collections4.MapUtils;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Joiner;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class OkHttpUtil {

    private static final long CONNECT_TIMEOUT = 10;
    private static final long READ_TIMEOUT = 10;
    private static final long WRITE_TIMEOUT = 30;

    private static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");

    private HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(s -> System.out.println(s));

    private OkHttpClient httpClient;

    private OkHttpUtil() {
        //        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        httpClient = new OkHttpClient().newBuilder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .sslSocketFactory(createSSLSocketFactory(), new TrustAllCerts())
                .hostnameVerifier((s, sslSession) -> true)
                .addNetworkInterceptor(loggingInterceptor)
                .build();
    }

    private static class OkHttp3Holder {
        private static OkHttpUtil INSTANCE = new OkHttpUtil();
    }

    public static OkHttpUtil getInstance() {
        return OkHttp3Holder.INSTANCE;
    }

    private static Request.Builder addHeaders(Map<String, String> headerMap, Request.Builder builder) {
        if (headerMap != null && !headerMap.isEmpty()) {
            headerMap.forEach((key, value) -> builder.addHeader(key, value));
        }
        builder.removeHeader("User-Agent");
        builder.addHeader("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 14_4_2 like Mac OS X) AppleWebKit/605.1"
                + ".15 (KHTML, like Gecko) Mobile/15E148");
        return builder;
    }

    private Response executeRequest(Request request) {
        Call call = httpClient.newCall(request);
        Response response = null;
        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    public Response get(String url) {
        return get(url, null, null);
    }

    public Response get(String url, Map<String, String> params) {
        return get(url, params, null);
    }

    public Response get(String url, Map<String, String> params, Map<String, String> headerMap) {
        if (MapUtils.isNotEmpty(params)) {
            String formatParams = Joiner.on("&").useForNull("").withKeyValueSeparator("=").join(params);
            url = !url.contains("?") ? (url + "?" + formatParams)
                    : (url.substring(0, url.indexOf("?") + 1) + formatParams);
        }

        Request.Builder builder = new Request.Builder().get().url(url);
        addHeaders(headerMap, builder);
        return executeRequest(builder.build());
    }

    public Response postForm(String url, Map<String, String> bodyParams) {
        return postForm(url, null, bodyParams);
    }

    public Response postForm(String url, Map<String, String> urlParams, Map<String, String> bodyParams) {
        return postForm(url, urlParams, bodyParams, null);
    }

    public Response postForm(String url, Map<String, String> urlParams, Map<String, String> bodyParams,
                             Map<String, String> headerMap) {
        if (MapUtils.isNotEmpty(urlParams)) {
            String formatParams = Joiner.on("&").useForNull("").withKeyValueSeparator("=").join(urlParams);
            url = !url.contains("?") ? (url + "?" + formatParams)
                    : (url.substring(0, url.indexOf("?") + 1) + formatParams);
        }

        FormBody.Builder formEncodingBuilder = new FormBody.Builder();
        if (MapUtils.isNotEmpty(bodyParams)) {
            for (Map.Entry<String, String> entry : bodyParams.entrySet()) {
                formEncodingBuilder.add(entry.getKey(), entry.getValue());
            }
        }
        RequestBody body = formEncodingBuilder.build();

        Request.Builder builder = new Request.Builder().post(body).url(url);
        addHeaders(headerMap, builder);
        return executeRequest(builder.build());
    }

    public Response postJson(String url, JSONObject bodyParams) {
        return postJson(url, bodyParams, null);
    }

    public Response postJson(String url, JSONObject bodyParams, Map<String, String> headerMap) {
        if (bodyParams == null) {
            bodyParams = new JSONObject();
        }
        RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, bodyParams.toJSONString());
        Request.Builder builder = new Request.Builder().post(body).url(url);
        addHeaders(headerMap, builder);
        return executeRequest(builder.build());
    }

    public SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory ssfFactory = null;
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[] {new TrustAllCerts()}, new SecureRandom());
            ssfFactory = sc.getSocketFactory();
        } catch (Exception e) {
        }
        return ssfFactory;
    }

    class TrustAllCerts implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }

}