package com.summer.grpc.consumer.tp.service;

import com.summer.grpc.consumer.service.GreeterGrpc;
import com.summer.grpc.consumer.service.HelloReply;
import com.summer.grpc.consumer.service.HelloRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author liuwei
 */
@Service
@Slf4j
public class GreeterService {

    @Resource
    private GreeterGrpc.GreeterBlockingStub  greeterBlockingStub;

    public void greet(String name){
        HelloRequest request = HelloRequest.newBuilder()
                .setName(name)
                .build();

        HelloReply helloReply = greeterBlockingStub.sayHello(request);
        log.info("-------{}",helloReply);
    }
}
