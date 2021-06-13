/*
 * Copyright (C) 2021 Baidu, Inc. All Rights Reserved.
 */
package com.icodeyou.teslaapi.lib;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.icodeyou.teslaapi.util.OkHttpUtil;
import com.icodeyou.teslaapi.model.VehicleList;

import okhttp3.Response;

public class VehicleUtil {

    /**
     * Get all vehicles belonging to the account.
     * @param accessToken
     * @return vehicle list
     */
    public static List<VehicleList.Vehicle> getVehicles(String accessToken) {
        VehicleList vehicles = null;

        Map<String, String> header = new HashMap<>();
        header.put("authorization", "Bearer " + accessToken);
        Response vehiclesResponse =
                OkHttpUtil.getInstance().get("https://owner-api.teslamotors.com/api/1/vehicles", null, header);
        try {
            vehicles = JSONObject.parseObject(vehiclesResponse.body().string(), VehicleList.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return vehicles.getResponse();
    }

    /**
     * Get the first vehicle belonging to the account.
     * @param accessToken
     * @return the first vehicle
     */
    public static Long getFirstVehicleId(String accessToken) {
        List<VehicleList.Vehicle> vehicles = getVehicles(accessToken);
        return vehicles.get(0).getId();
    }

}
