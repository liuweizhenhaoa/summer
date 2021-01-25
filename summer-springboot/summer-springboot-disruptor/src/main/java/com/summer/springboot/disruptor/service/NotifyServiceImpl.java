package com.summer.springboot.disruptor.service;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.summer.springboot.disruptor.config.NotifyEventFactory;
import com.summer.springboot.disruptor.config.NotifyEventHandler;
import com.summer.springboot.disruptor.config.NotifyEventHandlerException;
import com.summer.springboot.disruptor.entity.NotifyEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.Executors;

/**
 * 整合Spring，对Disruptor进行初始化
 * @author liuwei
 */
@Service
@Slf4j
public class NotifyServiceImpl implements INotifyService, DisposableBean, InitializingBean {
    private Disruptor<NotifyEvent> disruptor;
    private static final int RING_BUFFER_SIZE = 1024 * 1024;

    @Override
    public void destroy() {
        disruptor.shutdown();
    }

    @Override
    public void afterPropertiesSet() {
        disruptor = new Disruptor<NotifyEvent>(new NotifyEventFactory(), RING_BUFFER_SIZE, Executors.defaultThreadFactory(), ProducerType.SINGLE, new BlockingWaitStrategy());
        disruptor.setDefaultExceptionHandler(new NotifyEventHandlerException());
        disruptor.handleEventsWith(new NotifyEventHandler());
        disruptor.start();
        consume();
    }


    @Override
    public void sendNotify(String message) {
        RingBuffer<NotifyEvent> ringBuffer = disruptor.getRingBuffer();
//        ringBuffer.publishEvent(new EventTranslatorOneArg<NotifyEvent,  String>() {
//            @Override
//            public void translateTo(NotifyEvent event, long sequence, String data) {
//                event.setMessage(data);
//            }
//        }, message);

        //lambda式写法，如果是用jdk1.8以下版本使用以上注释的一段
        ringBuffer.publishEvent((event, sequence, data) -> event.setMessage(data).setTime(new Date()), message);
    }

    @Override
    public void consume() {
        log.info("---------------begin consume-------------------");

        //可以把ringBuffer看作是一个事件队列，那么next就是得到下一个事件槽
        RingBuffer<NotifyEvent> ringBuffer = disruptor.getRingBuffer();
        long sequence = ringBuffer.next();
        try {
            NotifyEvent notification = ringBuffer.get(sequence);

            log.info("------------consume:" + notification.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}