/*
 * Copyright (C) 2021 Baidu, Inc. All Rights Reserved.
 */
package com.icodeyou.teslaapi.lib;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.icodeyou.teslaapi.consts.UrlConsts;
import com.icodeyou.teslaapi.model.CommandResult;
import com.icodeyou.teslaapi.util.OkHttpUtil;

import okhttp3.Response;

public class ChargingUtil {

    public static Boolean openChargePortDoor(String accessToken, Long vehicleId) {
        Map<String, String> header = new HashMap<>();
        header.put("authorization", "Bearer " + accessToken);
        Response response = OkHttpUtil.getInstance()
                .postJson(String.format(UrlConsts.COMMAND, vehicleId, "charge_port_door_open"),
                        null, header);
        CommandResult commandResult = null;
        try {
            commandResult = JSONObject.parseObject(response.body().string(), CommandResult.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return commandResult.getResponse().getResult();
    }

    public static Boolean closeChargePortDoor(String accessToken, Long vehicleId) {
        Map<String, String> header = new HashMap<>();
        header.put("authorization", "Bearer " + accessToken);
        Response response = OkHttpUtil.getInstance()
                .postJson(String.format(UrlConsts.COMMAND, vehicleId, "charge_port_door_close"),
                        null, header);
        CommandResult commandResult = null;
        try {
            commandResult = JSONObject.parseObject(response.body().string(), CommandResult.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return commandResult.getResponse().getResult();
    }

    public static Boolean setChargeLimit(String accessToken, Long vehicleId, Integer limit) {
        Map<String, String> header = new HashMap<>();
        header.put("authorization", "Bearer " + accessToken);
        JSONObject params = new JSONObject();
        params.put("percent", limit);
        Response response = OkHttpUtil.getInstance()
                .postJson(String.format(UrlConsts.COMMAND, vehicleId, "set_charge_limit"),
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
