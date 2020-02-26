package com.summer.springboot.disruptor.config;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;
import com.summer.springboot.disruptor.entity.NotifyEvent;
import lombok.extern.slf4j.Slf4j;
import java.text.SimpleDateFormat;
import java.util.UUID;

/**
 * 创建消费者，此处用于处理业务逻辑
 */
@Slf4j
public class NotifyEventHandler implements EventHandler<NotifyEvent>, WorkHandler<NotifyEvent> {

    @Override
    public void onEvent(NotifyEvent notifyEvent, long l, boolean b) throws Exception {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        log.info("接收到消息  id:" + notifyEvent.getId() + "------date:" + f.format(notifyEvent.getTime()));
        this.onEvent(notifyEvent);
    }

    @Override
    public void onEvent(NotifyEvent notifyEvent) throws Exception {
        log.info(notifyEvent + ">>>" + UUID.randomUUID().toString());
    }
}