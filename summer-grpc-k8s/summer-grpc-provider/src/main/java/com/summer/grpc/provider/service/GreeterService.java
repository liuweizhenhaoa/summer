package com.summer.grpc.provider.service;

import com.summer.grpc.consumer.service.GreeterGrpc;
import com.summer.grpc.consumer.service.HelloReply;
import com.summer.grpc.consumer.service.HelloRequest;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.GRpcService;

/**
 * @author liuwei
 */
@GRpcService
@Slf4j
public class GreeterService extends GreeterGrpc.GreeterImplBase {

    @Override
    public void sayHello(HelloRequest request, StreamObserver<HelloReply> responseObserver) {
        log.info("provider-----{}",request.toString());
        HelloReply helloReply =     HelloReply.newBuilder()
                .setMessage("hello ---------"+request.getName())
                .build();
        responseObserver.onNext(helloReply);
        responseObserver.onCompleted();
    }
}
