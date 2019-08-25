package com.summer.springboot.webflux.controller;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import	java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RunWith(SpringRunner.class)
@SpringBootTest()
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


    public static void main(String[] args) {
        TestCase test = new TestCase();
        test.testMonoBasic();
        test.testFluxBasic();
    }
}
