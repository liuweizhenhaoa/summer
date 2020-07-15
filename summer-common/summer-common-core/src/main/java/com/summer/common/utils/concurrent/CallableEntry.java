/*
 * @(#) CallableEntry.java 2020-07-08
 *
 * Copyright 2020 NetEase.com, Inc. All rights reserved.
 */

package com.summer.common.utils.concurrent;

import java.util.concurrent.Callable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liuwei08
 * @version 2020-07-08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CallableEntry<T> {

    private Callable<T> callable;
    private String routerKey;
}
