/*
 * @(#) DataFilter.java 2020-04-15
 *
 * Copyright 2020 NetEase.com, Inc. All rights reserved.
 */

package com.summer.springboot.groovy.filter;

/**
 * @author liuwei08
 * @version 2020-04-15
 */
public abstract class DataFilter implements Filter{

    private DataFilter nextDataFilter;

    public void process(String req) {
        doFilter(req);

        if (nextDataFilter!= null){
            nextDataFilter.process(req);
        }
    }

    public void setNextDataFilter(DataFilter nextDataFilter) {
        this.nextDataFilter = nextDataFilter;
    }
}
