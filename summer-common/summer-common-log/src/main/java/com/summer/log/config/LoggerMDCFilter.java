package com.summer.log.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.MDC;
import org.springframework.web.filter.OncePerRequestFilter;

import com.summer.common.constants.CommonConstants;
import com.summer.common.utils.MDCUtils;

/**
 * 日志 MDC 过滤器，打印输入 x-request-id
 */
public class LoggerMDCFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String requestId = request.getHeader("x-request-id");
            if (null == requestId || "".equals(requestId)) {
                requestId = MDCUtils.getUUID();
            }

            MDC.put(CommonConstants.TRACE_ID, requestId);
            filterChain.doFilter(request, response);
        } finally {
            clearMDC();
        }
    }

    private void clearMDC() {
        MDC.clear();
    }
    
}
