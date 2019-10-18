package com.summer.springboot.webflux.service;

import com.summer.springboot.webflux.entity.Order;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Service
public class OrderService {

    private final Map<Long, Order> data = new ConcurrentHashMap<>();

    public Flux<Order> list() {
        return Flux.fromIterable(data.values());
    }

    public Flux<Order> getByIds(final Flux<Long> ids) {
        return ids.flatMap(id -> Mono.justOrEmpty(this.data.get(id)));
    }

    public Mono<Order> getById(final Long id) {
        return Mono.justOrEmpty(this.data.get(id))
                .switchIfEmpty(Mono.error(new RuntimeException("" + id)));
    }

    public Mono<Order> createOrUpdate(final Order order) {
        this.data.put(order.getId(), order);
        return Mono.just(order);
    }

    public Mono<Order> delete(final Long id) {
        return Mono.justOrEmpty(this.data.remove(id));
    }

}
