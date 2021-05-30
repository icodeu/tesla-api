/*
 * Copyright (C) 2021 Baidu, Inc. All Rights Reserved.
 */
package com.icodeyou.teslaapi.util;

import org.apache.commons.lang3.StringUtils;

public class EnsureUtil {

    public static void notEmpty(String content, String errorMsg) {
        if (StringUtils.isEmpty(content)) {
            throw new RuntimeException(errorMsg);
        }
    }

}
