/*
 * Copyright (C) 2021 Baidu, Inc. All Rights Reserved.
 */
package com.icodeyou.teslaapi;

import com.icodeyou.teslaapi.lib.AuthUtil;
import com.icodeyou.teslaapi.lib.StateUtil;
import com.icodeyou.teslaapi.lib.VehicleUtil;
import com.icodeyou.teslaapi.model.ClimateState;

public class Main {

    public static void main(String[] args) {
        String accessToken = AuthUtil.login("your email", "your password");

        Long vehicleId = VehicleUtil.getFirstVehicleId(accessToken);
        System.out.println(vehicleId);

        ClimateState climateState = StateUtil.getClimateState(accessToken, vehicleId);
        System.out.println(climateState);
    }

}
