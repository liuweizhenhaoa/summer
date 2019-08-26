package com.summer.springboot.webflux.controller;

import com.summer.springboot.webflux.entity.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = OrderController.class)
public class OrderControllerTest {

    @Test
    public void queryOrder() {
        final Order order = new Order();
        order.setEmail("test@example.org");
        final WebClient client = WebClient.create("http://localhost:8080/user");
        final Mono<Order> createdUser = client.post().uri("").accept(MediaType.APPLICATION_JSON).body(Mono.just(order), Order.class).exchange()
                        .flatMap(response -> response.bodyToMono(Order.class));
        System.out.println(createdUser.block());
    }



}