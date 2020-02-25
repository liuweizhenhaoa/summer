package com.summer.springboot.disruptor.config;

import com.lmax.disruptor.EventFactory;
import com.summer.springboot.disruptor.entity.NotifyEvent;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 创建消息工厂用于生产消息
 */
@Slf4j
public class NotifyEventFactory implements EventFactory {

    private static final AtomicLong EVENT_ID = new AtomicLong(0);

    @Override
    public Object newInstance() {
        return new NotifyEvent()
                .setId(EVENT_ID.getAndIncrement());
    }
}