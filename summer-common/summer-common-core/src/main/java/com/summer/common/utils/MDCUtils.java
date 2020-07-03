/*
 * @(#) MDCUtils.java 2020-07-03
 *
 * Copyright 2020 NetEase.com, Inc. All rights reserved.
 */

package com.summer.common.utils;

import java.util.UUID;

/**
 * @author liuwei08
 * @version 2020-07-03
 */
public class MDCUtils {

    /**
     * 获取UUID
     * @return String UUID
     */
    public static String getUUID(){
        String uuid = UUID.randomUUID().toString();
        //替换-字符
        return uuid.replaceAll("-", "");
    }

}
