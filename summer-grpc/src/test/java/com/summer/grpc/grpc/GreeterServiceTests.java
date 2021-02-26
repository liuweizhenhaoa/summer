package com.summer.grpc.grpc;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.Uninterruptibles;
import com.google.protobuf.Empty;
import com.summer.grpc.GrpcApplication;
import com.summer.grpc.proto.GreeterGrpc;
import com.summer.grpc.proto.GreeterOuterClass;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

/**
 * GreeterServiceTests
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {GrpcApplication.class}, webEnvironment = WebEnvironment.NONE)
@Slf4j
public class GreeterServiceTests {
    ManagedChannel channel;

    @Before
    public void initChannel() {
        this.channel = ManagedChannelBuilder.forAddress("localhost", 6565)
                .usePlaintext()
                .build();
    }

    @Test
    public void greeterTest() throws Exception {
        GreeterGrpc.GreeterFutureStub stub = GreeterGrpc.newFutureStub(channel);
        ListenableFuture<GreeterOuterClass.HelloReply> response = stub.sayHello(GreeterOuterClass.HelloRequest.newBuilder().setName("jack").build());
        
        final CountDownLatch latch = new CountDownLatch(1);
        Futures.addCallback(response, new FutureCallback<GreeterOuterClass.HelloReply>() {

            @Override
            public void onSuccess(GreeterOuterClass.HelloReply result) {
                String message = result.getMessage();
                log.info("message: {}", message);
                assertTrue(message.contains("jack"));
                latch.countDown();
            }

            @Override
            public void onFailure(Throwable t) {
                log.error("Hello error: {}", t);
                latch.countDown();
            }
        }, MoreExecutors.directExecutor());

        if (!Uninterruptibles.awaitUninterruptibly(latch, 1, TimeUnit.SECONDS)) {
            throw new RuntimeException("timeout!");
        }
    }

    @Test
    public void timeTest() throws Exception {
        GreeterGrpc.GreeterBlockingStub stub = GreeterGrpc.newBlockingStub(channel);
        String currentTime = stub.currentTime(Empty.newBuilder().build()).getMessage();

        log.info("current time: {}", currentTime);
    }

}