/*
 * Copyright (C) 2021 Baidu, Inc. All Rights Reserved.
 */
package com.icodeyou.teslaapi.lib;

import com.alibaba.fastjson.JSONObject;
import com.icodeyou.teslaapi.consts.UrlConsts;
import com.icodeyou.teslaapi.model.ChargeState;
import com.icodeyou.teslaapi.model.ClimateState;
import com.icodeyou.teslaapi.util.OkHttpUtil;
import okhttp3.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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

    /**
     * Get the current charge state
     * @param accessToken
     * @param vehicleId
     * @return
     */
    public static ChargeState getChargeState(String accessToken, Long vehicleId) {
        Map<String, String> header = new HashMap<>();
        header.put("authorization", "Bearer " + accessToken);
        Response response = OkHttpUtil.getInstance()
                .get(String.format(UrlConsts.STATE, vehicleId, "charge_state"),
                        null, header);
        ChargeState chargeState = null;
        try {
            chargeState = JSONObject.parseObject(response.body().string(), ChargeState.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return chargeState;
    }

}
