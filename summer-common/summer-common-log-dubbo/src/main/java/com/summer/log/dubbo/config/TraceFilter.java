package com.summer.log.dubbo.config;

import java.util.UUID;

import org.apache.dubbo.rpc.Filter;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Result;
import org.apache.dubbo.rpc.RpcException;
import org.slf4j.MDC;

import com.summer.common.constants.CommonConstants;

public class TraceFilter implements Filter {

    @Override
    public Result invoke(Invoker<?> invoker, org.apache.dubbo.rpc.Invocation invocation) throws RpcException {
        // trace_id 可以从请求参数中获取
        MDC.put(CommonConstants.TRACE_ID, UUID.randomUUID().toString());
        return invoker.invoke(invocation);
    }
}
