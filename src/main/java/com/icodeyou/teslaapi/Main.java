/*
 * Copyright (C) 2021 Baidu, Inc. All Rights Reserved.
 */
package com.icodeyou.teslaapi;

import com.icodeyou.teslaapi.lib.AuthUtil;
import com.icodeyou.teslaapi.lib.ChargingUtil;
import com.icodeyou.teslaapi.lib.ClimateUtil;
import com.icodeyou.teslaapi.lib.StateUtil;
import com.icodeyou.teslaapi.lib.TrunkUtil;
import com.icodeyou.teslaapi.lib.VehicleUtil;
import com.icodeyou.teslaapi.lib.WakeUtil;
import com.icodeyou.teslaapi.model.ClimateState;

public class Main {

    public static void main(String[] args) {
        String accessToken = AuthUtil.login("your email", "your password");

        Long vehicleId = VehicleUtil.getFirstVehicleId(accessToken);
        System.out.println(vehicleId);

        WakeUtil.wakeUp(accessToken, vehicleId);

        ClimateUtil.setTemperature(accessToken, vehicleId, 18);
        ClimateUtil.startAutoConditioning(accessToken, vehicleId);
        ClimateUtil.stopAutoConditioning(accessToken, vehicleId);
        ClimateUtil.setSeatHeater(accessToken, vehicleId, 0, 0);
        ClimateUtil.setWheelHeater(accessToken, vehicleId, false);
        ClimateState climateState = StateUtil.getClimateState(accessToken, vehicleId);
        System.out.println(climateState);

        ChargingUtil.openChargePortDoor(accessToken, vehicleId);
        ChargingUtil.closeChargePortDoor(accessToken, vehicleId);
        ChargingUtil.setChargeLimit(accessToken, vehicleId, 85);

        TrunkUtil.actuateTrunk(accessToken, vehicleId, "rear");
    }

}
