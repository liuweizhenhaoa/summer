/*
 * @(#) ProviderRpcTraceFilter.java 2020-07-03
 *
 * Copyright 2020 NetEase.com, Inc. All rights reserved.
 */

package com.summer.log.dubbo.config;

import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.common.utils.StringUtils;
import org.apache.dubbo.rpc.Filter;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Result;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.rpc.RpcException;
import org.slf4j.MDC;

import com.alibaba.dubbo.common.Constants;
import com.summer.common.constants.CommonConstants;
import com.summer.common.utils.MDCUtils;

/**
 * 提供者 设置MDC
 * @author liuwei08
 * @version 2020-07-03
 */
@Activate(group = {Constants.PROVIDER},order = 1)
public class ProviderRpcTraceFilter implements Filter {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        String traceId = RpcContext.getContext().getAttachment(CommonConstants.TRACE_ID);
        if (StringUtils.isBlank(traceId)) {
            traceId = MDCUtils.getUUID() ;
        }

        //设置日志traceId变量
        MDC.put(CommonConstants.TRACE_ID, traceId);

        RpcContext.getContext().setAttachment(CommonConstants.TRACE_ID, traceId);

        try{
            return invoker.invoke(invocation);
        }finally {
            MDC.remove(CommonConstants.TRACE_ID);
        }
    }


}
