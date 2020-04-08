package com.summer.mq.kafka.listener;

import com.summer.mq.kafka.model.Bar2;
import com.summer.mq.kafka.model.Foo2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(id = "multiGroup", topics = { "foos", "bars" })
@Slf4j
public class MultiMethodsListener {

    @KafkaHandler
    public void foo(Foo2 foo) {
        log.info("Received: " + foo);
    }

    @KafkaHandler
    public void bar(Bar2 bar) {
        log.info("Received: " + bar);
    }

    @KafkaHandler(isDefault = true)
    public void unknown(Object object) {
        log.info("Received unknown: " + object);
    }
}
