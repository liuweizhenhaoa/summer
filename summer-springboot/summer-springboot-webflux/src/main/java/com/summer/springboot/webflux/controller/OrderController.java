package com.summer.springboot.webflux.controller;

import com.summer.springboot.webflux.entity.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.Date;

@RestController
@RequestMapping("/api/reactive")
@Slf4j
public class OrderController {



    @GetMapping("/test")
    public Flux<Order> queryOrder() {

        return Flux.just(
                Order.builder().id(1).createTime(new Date()).detail("aaa").email("123@163.com").build(),
                Order.builder().id(2).createTime(new Date()).detail("bbbb").email("345@163.com").build()
        );


    }


}
