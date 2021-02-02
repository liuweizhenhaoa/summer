package com.summer.grpc.consumer.config;

import com.summer.grpc.consumer.service.GreeterGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author liuwei
 */
@Configuration
public class GrpcConfiguration {

    @Value("${grpc.customer.server.name}")
    private String name;
    @Value("${grpc.customer.server.port}")
    private int port;


    @Bean
    public ManagedChannel channel() {
        // Create a communication channel to the server, known as a Channel. Channels are thread-safe
        // and reusable. It is common to create channels at the beginning of your application and reuse
        // them until the application shuts down.
        ManagedChannel channel = ManagedChannelBuilder.forAddress(name, port)
                // Channels are secure by default (via SSL/TLS). For the example we disable TLS to avoid
                // needing certificates.
                .usePlaintext()
                .build();

        return channel;
    }

    @Bean
    public GreeterGrpc.GreeterBlockingStub getGreeterBlockingStub() {
        return GreeterGrpc.newBlockingStub(channel());
    }
}
