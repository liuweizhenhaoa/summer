package com.summer.sharding.Controller;


import com.summer.sharding.entity.Order;
import com.summer.sharding.entity.OrderInfo;
import com.summer.sharding.entity.OrderRequest;
import com.summer.sharding.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderController {

    @Autowired
    OrderService orderService;

    @RequestMapping(value = "/order", method = RequestMethod.POST)
    public List<Order> getOrder(@RequestBody OrderRequest orderRequest) {
        return orderService.getOrder(orderRequest);
    }


    @RequestMapping(value = "/getOrderByOrderId", method = RequestMethod.POST)
    public List<Order> getOrderByOrderId(@RequestBody OrderRequest orderRequest) {
        return orderService.getOrderById(orderRequest);
    }


    @RequestMapping(value = "/orders", method = RequestMethod.GET)
    public List<Order> getOrders() {
        return orderService.getOrders();
    }

    @RequestMapping(value = "/addOrder", method = RequestMethod.POST)
    public void addOrder(@RequestBody Order order) {
        orderService.addOrder(order);
    }

    @RequestMapping(value = "/orderInfo", method = RequestMethod.POST)
    public List<OrderInfo> getOrderInfo(@RequestBody OrderRequest orderRequest) {
        return orderService.getOrderInfo(orderRequest);
    }
}
