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

public class TrunkUtil {

    /**
     * Open or close the trunk. Please note the rear trunk can be open or close, but the front trunk can be open
     * only, if close, you must press it in front of your car.
     * @param accessToken
     * @param vehicleId
     * @param whichTrunk front or rear
     * @return
     */
    public static Boolean actuateTrunk(String accessToken, Long vehicleId, String whichTrunk) {
        Map<String, String> header = new HashMap<>();
        header.put("authorization", "Bearer " + accessToken);
        JSONObject params = new JSONObject();
        params.put("which_trunk", whichTrunk);
        Response response = OkHttpUtil.getInstance()
                .postJson(String.format(UrlConsts.COMMAND, vehicleId, "actuate_trunk"),
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
