/*
 * Copyright (C) 2021 Baidu, Inc. All Rights Reserved.
 */
package com.icodeyou.teslaapi.lib;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.icodeyou.teslaapi.util.OkHttpUtil;
import com.icodeyou.teslaapi.consts.UrlConsts;
import com.icodeyou.teslaapi.model.CommandResult;

import okhttp3.Response;

public class ClimateUtil {

    public static Boolean startAutoConditioning(String accessToken, Long vehicleId) {
        Map<String, String> header = new HashMap<>();
        header.put("authorization", "Bearer " + accessToken);
        Response response = OkHttpUtil.getInstance()
                .postJson(String.format(UrlConsts.COMMAND, vehicleId, "auto_conditioning_start"),
                        null, header);
        CommandResult commandResult = null;
        try {
            commandResult = JSONObject.parseObject(response.body().string(), CommandResult.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return commandResult.getResponse().getResult();
    }

    public static Boolean stopAutoConditioning(String accessToken, Long vehicleId) {
        Map<String, String> header = new HashMap<>();
        header.put("authorization", "Bearer " + accessToken);
        Response response = OkHttpUtil.getInstance()
                .postJson(String.format(UrlConsts.COMMAND, vehicleId, "auto_conditioning_stop"),
                        null, header);
        CommandResult commandResult = null;
        try {
            commandResult = JSONObject.parseObject(response.body().string(), CommandResult.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return commandResult.getResponse().getResult();
    }

    public static Boolean setTemperature(String accessToken, Long vehicleId, Integer temperature) {
        Map<String, String> header = new HashMap<>();
        header.put("authorization", "Bearer " + accessToken);
        JSONObject params = new JSONObject();
        params.put("driver_temp", temperature);
        params.put("passenger_temp", temperature);
        Response response = OkHttpUtil.getInstance()
                .postJson(String.format(UrlConsts.COMMAND, vehicleId, "set_temps"),
                        params, header);
        CommandResult commandResult = null;
        try {
            commandResult = JSONObject.parseObject(response.body().string(), CommandResult.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return commandResult.getResponse().getResult();
    }

}
