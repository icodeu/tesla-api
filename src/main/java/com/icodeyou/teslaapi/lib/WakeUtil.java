/*
 * Copyright (C) 2021 Baidu, Inc. All Rights Reserved.
 */
package com.icodeyou.teslaapi.lib;

import com.alibaba.fastjson.JSONObject;
import com.icodeyou.teslaapi.model.WakeUp;
import com.icodeyou.teslaapi.util.OkHttpUtil;
import okhttp3.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class WakeUtil {

    /**
     * Wake up the vehicle from sleep mode by sending a sms to the car
     * @param accessToken
     * @param vehicleId
     * @return True - Wake up success, False - Wake up fail
     */
    public static Boolean wakeUp(String accessToken, Long vehicleId) {
        Map<String, String> header = new HashMap<>();
        header.put("authorization", "Bearer " + accessToken);
        int retryCount = 0;
        boolean wakeUpSuccess = false;
        while (!wakeUpSuccess && retryCount < 60) {
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
