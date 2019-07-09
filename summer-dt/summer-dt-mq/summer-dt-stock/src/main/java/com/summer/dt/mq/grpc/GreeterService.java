package com.summer.dt.mq.grpc;

import org.lognet.springboot.grpc.GRpcService;

import io.grpc.stub.StreamObserver;

import com.google.protobuf.Empty;
import com.jintdev.bus.proto.GreeterGrpc;
import com.jintdev.bus.proto.GreeterOuterClass;
import com.jintdev.bus.proto.GreeterOuterClass.HelloReply;

/**
 * GreeterService
 */
@GRpcService
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