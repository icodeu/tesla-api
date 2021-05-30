/*
 * Copyright (C) 2021 Baidu, Inc. All Rights Reserved.
 */
package com.icodeyou.teslaapi.model;

import java.util.List;

import lombok.Data;

@Data
public class WakeUp {

    private Response response;

    @Data
    public static class Response {
        private Long id;
        private Long user_id;
        private Long vehicle_id;
        private String vin;
        private String display_name;
        private String option_codes;
        private Object color;
        private List<String> tokens;
        private String state;
        private Boolean in_service;
        private String id_s;
        private Boolean calendar_enabled;
        private Integer api_version;
        private Object backseat_token;
        private Object backseat_token_updated_at;
    }

}
