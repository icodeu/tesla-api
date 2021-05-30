/*
 * Copyright (C) 2021 Baidu, Inc. All Rights Reserved.
 */
package com.icodeyou.teslaapi.model;

import lombok.Data;

@Data
public class ClimateState {

    private Response response;

    @Data
    public static class Response {
        private Boolean battery_heater;
        private Boolean battery_heater_no_power;
        private String climate_keeper_mode;
        private Integer defrost_mode;
        private Double driver_temp_setting;
        private Integer fan_status;
        private Double inside_temp;
        private Boolean is_auto_conditioning_on;
        private Boolean is_climate_on;
        private Boolean is_front_defroster_on;
        private Boolean is_preconditioning;
        private Boolean is_rear_defroster_on;
        private Integer left_temp_direction;
        private Double max_avail_temp;
        private Double min_avail_temp;
        private Double outside_temp;
        private Double passenger_temp_setting;
        private Boolean remote_heater_control_enabled;
        private Integer right_temp_direction;
        private Integer seat_heater_left;
        private Integer seat_heater_right;
        private Boolean side_mirror_heaters;
        private Long timestamp;
        private Boolean wiper_blade_heater;
    }

}
