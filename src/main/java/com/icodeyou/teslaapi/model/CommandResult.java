/*
 * Copyright (C) 2021 Baidu, Inc. All Rights Reserved.
 */
package com.icodeyou.teslaapi.model;

import lombok.Data;

@Data
public class CommandResult {

    private Response response;

    @Data
    public static class Response {
        private String reason;
        private Boolean result;
    }

}
