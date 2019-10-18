package com.summer.springboot.webflux.controller;

import com.summer.springboot.webflux.entity.Order;
import com.summer.springboot.webflux.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.Objects;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {


    @Autowired
    OrderService orderService;

    @GetMapping("/test")
    public Flux<Order> queryOrder() throws InterruptedException {

        Thread.sleep(5000);

        return Flux.just(
                Order.builder().id(1).createTime(new Date()).detail("aaa").email("123@163.com").build(),
                Order.builder().id(2).createTime(new Date()).detail("bbbb").email("345@163.com").build()
        );

    }

//    @GetMapping("/test")
//    public Mono<String> test() throws InterruptedException {
//
//        return Mono.just("test");
//
//    }

    @GetMapping("")
    public Flux<Order> list() {
        return this.orderService.list();
    }

    @GetMapping("/{id}")
    public Mono<Order> getById(@PathVariable("id") final long id) {
        return this.orderService.getById(id);
    }

    @PostMapping("")
    public Mono<Order> create(@RequestBody final Order user) {
        return this.orderService.createOrUpdate(user);
    }

    @PutMapping("/{id}")
    public Mono<Order> update(@PathVariable("id") final long id, @RequestBody final Order order) {
        Objects.requireNonNull(order);
        order.setId(id);
        return this.orderService.createOrUpdate(order);
    }

    @DeleteMapping("/{id}")
    public Mono<Order> delete(@PathVariable("id") final long id) {
        return this.orderService.delete(id);
    }


}
