package com.summer.sharding.controller;


import com.summer.sharding.entity.Order;
import com.summer.sharding.entity.OrderInfo;
import com.summer.sharding.entity.OrderRequest;
import com.summer.sharding.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping(value = "/order")
    public List<Order> getOrder(@RequestBody OrderRequest orderRequest) {
        return orderService.getOrder(orderRequest);
    }


    @PostMapping(value = "/getOrderByOrderId")
    public List<Order> getOrderByOrderId(@RequestBody OrderRequest orderRequest) {
        return orderService.getOrderById(orderRequest);
    }


    @GetMapping(value = "/orders")
    public List<Order> getOrders() {
        return orderService.getOrders();
    }

    @PostMapping(value = "/addOrder")
    public void addOrder(@RequestBody Order order) {
        orderService.addOrder(order);
    }

    @PostMapping(value = "/orderInfo")
    public List<OrderInfo> getOrderInfo(@RequestBody OrderRequest orderRequest) {
        return orderService.getOrderInfo(orderRequest);
    }
}
