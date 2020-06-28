/*
 * @(#) StorageFilter.java 2020-04-15
 *
 * Copyright 2020 NetEase.com, Inc. All rights reserved.
 */

package com.summer.springboot.groovy.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 存储
 * @author liuwei08
 * @version 2020-04-15
 */
@Order(1)
@Component
@Slf4j
public class StorageFilter  extends DataFilter{

    @Override
    public void doFilter(String req) {
        log.info("存储:{}", req);
        //TODO 存储到hbase

    }
}
