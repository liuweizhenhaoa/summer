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
 * 消费者 这是MDC
 * 日志染色ProviderRpcTraceFilter
 *
 * @author phpdragon
 */
@Activate(group = {Constants.CONSUMER})
public class ConsumerRpcTraceFilter implements Filter {

    /**
     * @param invoker
     * @param invocation
     * @return
     * @throws RpcException
     */
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        String traceId = MDC.get(CommonConstants.TRACE_ID);
        if (StringUtils.isBlank(traceId)) {
            traceId = MDCUtils.getUUID();
        }

        RpcContext.getContext().setAttachment(CommonConstants.TRACE_ID, traceId);
        return invoker.invoke(invocation);
    }





}
