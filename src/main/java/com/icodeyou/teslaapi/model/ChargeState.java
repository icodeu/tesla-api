/*
 * Copyright (C) 2021 Baidu, Inc. All Rights Reserved.
 */
package com.icodeyou.teslaapi.model;

import lombok.Data;

@Data
public class ChargeState {

    private Response response;

    @Data
    public static class Response {
        private Boolean battery_heater_on;
        private Integer battery_level;
        private Double battery_range;
        private Integer charge_current_request;
        private Integer charge_current_request_max;
        private Boolean charge_enable_request;
        private Double charge_energy_added;
        private Integer charge_limit_soc;
        private Integer charge_limit_soc_max;
        private Integer charge_limit_soc_min;
        private Integer charge_limit_soc_std;
        private Double charge_miles_added_ideal;
        private Double charge_miles_added_rated;
        private Object charge_port_cold_weather_mode;
        private Boolean charge_port_door_open;
        private String charge_port_latch;
        private Double charge_rate;
        private Boolean charge_to_max_range;
        private Integer charger_actual_current;
        private Object charger_phases;
        private Integer charger_pilot_current;
        private Integer charger_power;
        private Integer charger_voltage;
        private String charging_state;
        private String conn_charge_cable;
        private Double est_battery_range;
        private String fast_charger_brand;
        private Boolean fast_charger_present;
        private String fast_charger_type;
        private Double ideal_battery_range;
        private Boolean managed_charging_active;
        private Object managed_charging_start_time;
        private Boolean managed_charging_user_canceled;
        private Integer max_range_charge_counter;
        private Integer minutes_to_full_charge;
        private Boolean not_enough_power_to_heat;
        private Boolean scheduled_charging_pending;
        private Object scheduled_charging_start_time;
        private Double time_to_full_charge;
        private Long timestamp;
        private Boolean trip_charging;
        private Integer usable_battery_level;
        private Object user_charge_enable_request;
    }

}
