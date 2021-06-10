/*
 * Copyright (C) 2021 Baidu, Inc. All Rights Reserved.
 */
package com.icodeyou.teslaapi.lib;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.icodeyou.teslaapi.util.OkHttpUtil;
import com.icodeyou.teslaapi.model.WakeUp;

import okhttp3.Response;

public class WakeUtil {

    public static Boolean wakeUp(String accessToken, Long vehicleId) {
        Map<String, String> header = new HashMap<>();
        header.put("authorization", "Bearer " + accessToken);
        int retryCount = 0;
        boolean wakeUpSuccess = false;
        while (!wakeUpSuccess && retryCount < 20) {
            Response response = OkHttpUtil.getInstance().postJson(
                    String.format("https://owner-api.teslamotors.com/api/1/vehicles/%s/wake_up", vehicleId),
                    null, header);
            WakeUp wakeUp = null;
            try {
                wakeUp = JSONObject.parseObject(response.body().string(), WakeUp.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
            wakeUpSuccess = "online".equals(wakeUp.getResponse().getState());
            if (wakeUpSuccess) {
                return true;
            } else {
                retryCount++;
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

}
