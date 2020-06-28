/*
 * @(#) Filter.java 2020-04-15
 *
 * Copyright 2020 NetEase.com, Inc. All rights reserved.
 */

package com.summer.springboot.groovy.filter;

/**
 * @author liuwei08
 * @version 2020-04-15
 */
public interface Filter {
    public void doFilter(String req);
}
