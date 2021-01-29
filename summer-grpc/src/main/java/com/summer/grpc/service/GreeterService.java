package com.summer.grpc.service;

import com.google.protobuf.Empty;
import com.summer.grpc.interceptors.LogInterceptor;
import com.summer.grpc.proto.GreeterGrpc;
import com.summer.grpc.proto.GreeterOuterClass;
import com.summer.grpc.proto.GreeterOuterClass.HelloReply;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;

/**
 * GreeterService
 */
@GRpcService(interceptors = {LogInterceptor.class})
public class GreeterService extends GreeterGrpc.GreeterImplBase {
    @Override
    public void sayHello(GreeterOuterClass.HelloRequest request, StreamObserver<GreeterOuterClass.HelloReply> respoObserver) {
        final GreeterOuterClass.HelloReply.Builder replyBuilder = GreeterOuterClass.HelloReply.newBuilder()
                .setMessage("Hello: " + request.getName());

        respoObserver.onNext(replyBuilder.build());
        respoObserver.onCompleted();
    }

    @Override
    public void currentTime(Empty request, StreamObserver<HelloReply> responseObserver) {
        final HelloReply.Builder replyBuilder = HelloReply.newBuilder()
                .setMessage(String.valueOf(System.currentTimeMillis()));

        responseObserver.onNext(replyBuilder.build());
        responseObserver.onCompleted();
    }
}