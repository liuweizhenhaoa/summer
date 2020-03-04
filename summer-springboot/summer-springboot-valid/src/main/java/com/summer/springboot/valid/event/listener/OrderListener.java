package com.summer.springboot.valid.event.listener;

import com.summer.springboot.valid.event.entity.OrderEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderListener {

    @EventListener
//    @Async
    public void onApplicationEvent(OrderEvent orderEvent) {
        log.info("-----"+orderEvent.toString());
    }
}
