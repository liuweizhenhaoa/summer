package com.summer.springboot.cache.controller;

import com.summer.springboot.cache.entity.Order;
import com.summer.springboot.cache.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping("/saveOrder")
    public void saveOrder(Order order){

    }

    @PostMapping("/updateOrder")
    public void updateOrder(Order order){

    }

    @PostMapping("/queryOrder")
    public void queryOrder(Order order){

    }

    @PostMapping("/deleteOrder")
    public void deleteOrder(Order order){

    }
}
