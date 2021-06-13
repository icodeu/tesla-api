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

    /**
     * Start the air conditioner, make it work.
     * @param accessToken
     * @param vehicleId
     * @return
     */
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

    /**
     * Stop the air conditioner, make it stop.
     * @param accessToken
     * @param vehicleId
     * @return
     */
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

    /**
     * Set temperature of driver and passenger, but please note call startAutoConditioning method later to make air
     * conditioner work, or it may be not work.
     * @param accessToken
     * @param vehicleId
     * @return
     */
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

    /**
     * Open or close some seat heater
     * Note: Must call startAutoConditioning method before, and then call this method, or it will not take effect
     *
     * @param accessToken
     * @param vehicleId
     * @param heater      0-Front_Left  1-Front_Right  2-Rear_Left  4-Rear_Center  5-Rear_Right
     * @param level       From 0(Contains) To 3(Contains)
     * @return
     */
    public static Boolean setSeatHeater(String accessToken, Long vehicleId, Integer heater, Integer level) {
        Map<String, String> header = new HashMap<>();
        header.put("authorization", "Bearer " + accessToken);
        JSONObject params = new JSONObject();
        params.put("heater", heater);
        params.put("level", level);
        Response response = OkHttpUtil.getInstance()
                .postJson(String.format(UrlConsts.COMMAND, vehicleId, "remote_seat_heater_request"),
                        params, header);
        CommandResult commandResult = null;
        try {
            commandResult = JSONObject.parseObject(response.body().string(), CommandResult.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return commandResult.getResponse().getResult();
    }

    /**
     * Open or close steering wheel heater
     * Note: Must call startAutoConditioning method before, and then call this method, or it will not take effect
     *
     * @param accessToken
     * @param vehicleId
     * @param isOn        True or False
     * @return
     */
    public static Boolean setWheelHeater(String accessToken, Long vehicleId, boolean isOn) {
        Map<String, String> header = new HashMap<>();
        header.put("authorization", "Bearer " + accessToken);
        JSONObject params = new JSONObject();
        params.put("on", isOn);
        Response response = OkHttpUtil.getInstance()
                .postJson(String.format(UrlConsts.COMMAND, vehicleId, "remote_steering_wheel_heater_request"),
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
