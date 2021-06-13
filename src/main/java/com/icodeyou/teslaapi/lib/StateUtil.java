/*
 * Copyright (C) 2021 Baidu, Inc. All Rights Reserved.
 */
package com.icodeyou.teslaapi.lib;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.icodeyou.teslaapi.consts.UrlConsts;
import com.icodeyou.teslaapi.model.ClimateState;
import com.icodeyou.teslaapi.util.OkHttpUtil;

import okhttp3.Response;

public class StateUtil {

    /**
     * Get the current climate state, including temperature in the car, and so on.
     * @param accessToken
     * @param vehicleId
     * @return
     */
    public static ClimateState getClimateState(String accessToken, Long vehicleId) {
        Map<String, String> header = new HashMap<>();
        header.put("authorization", "Bearer " + accessToken);
        Response response = OkHttpUtil.getInstance()
                .get(String.format(UrlConsts.STATE, vehicleId, "climate_state"),
                        null, header);
        ClimateState climateState = null;
        try {
            climateState = JSONObject.parseObject(response.body().string(), ClimateState.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return climateState;
    }

}
