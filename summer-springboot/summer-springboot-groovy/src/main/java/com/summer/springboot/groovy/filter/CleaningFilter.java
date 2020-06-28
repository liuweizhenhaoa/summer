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
 * 清洗
 * @author liuwei08
 * @version 2020-04-15
 */
@Order(2)
@Component
@Slf4j
public class CleaningFilter extends DataFilter{
    
    @Override
    public void doFilter(String req) {
        log.info("清洗:{}", req);
    }
}
