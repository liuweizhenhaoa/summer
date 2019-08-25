package com.summer.springboot.webflux.client;

import com.summer.springboot.webflux.entity.Order;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Date;

public class RestClient {

    public static void main(String[] args) {
        final Order order = new Order();
        order.setId(1);
        order.setPrice(1);
        order.setDetail("dddqq");
        order.setEmail("asad@163.com");
        order.setCreateTime(new Date());
        final WebClient client = WebClient.create("http://localhost:8080/order");

        final Mono<Order> createdOrder =    client.post()
                                    .uri("")
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(order), Order.class)
                .exchange()
                .flatMap(response -> response.bodyToMono(Order.class));

        System.out.println(createdOrder.block());



    }
}
