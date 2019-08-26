package com.summer.springboot.webflux.controller;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import	java.util.Optional;

import com.summer.springboot.webflux.entity.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RunWith(SpringRunner.class)
//@SpringBootTest()
public class TestCase {

    @Test
    public void testMonoBasic(){
        Mono.fromSupplier(()->"hello").subscribe(System.out::println);
        Mono.justOrEmpty(Optional.of("Hello")).subscribe(System.out::println);
        Mono.create(sink-> sink.success("hello")).subscribe(System.out::println);
    }


    public void testFluxBasic(){
        Flux.just("1","2").subscribe(System.out::println);
        Flux.fromArray(new Integer [] {1,2,3}).subscribe(System.out::println);
        Flux.empty().subscribe(System.out::println);
        Flux.range(1,10).subscribe(System.out::println);
        Flux.interval(Duration.of(10, ChronoUnit.SECONDS)).subscribe(System.out::println);
    }

    private final WebTestClient client = WebTestClient.bindToServer().baseUrl("http://localhost:8080").build();

    @Test
    public void testCreateUser() throws Exception {
        final Order order = new Order();
        order.setEmail("test@example.org");
        client.post().uri("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(order), Order.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody().jsonPath("name").isEqualTo("Test");
    }


    public static void main(String[] args) {
        TestCase test = new TestCase();
        test.testMonoBasic();
        test.testFluxBasic();
    }
}
