/*
 * @(#) FilterConfiguration.java 2020-04-15
 *
 * Copyright 2020 NetEase.com, Inc. All rights reserved.
 */

package com.summer.springboot.groovy.filter.config;

import com.summer.springboot.groovy.filter.DataFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;

/**
 * @author liuwei08
 * @version 2020-04-15
 */
@Configuration
@Component
public class FilterConfiguration {

    @Autowired
    private List<DataFilter> filterList;

    @PostConstruct
    public void init() {
        Collections.sort(filterList, AnnotationAwareOrderComparator.INSTANCE);

        int size = filterList.size();
        for (int i = 0; i < size; i++) {
            if ( i == size-1) {
                filterList.get(i).setNextDataFilter(null);
            }else {
                filterList.get(i).setNextDataFilter(filterList.get(i+1));
            }
        }
    }

    public void doFilter(int index, String req) {
        filterList.get(index - 1).process(req);
    }

    public void doFilter( String req) {
        filterList.get(0).process(req);
    }

}
