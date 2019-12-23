package com.summer.springboot.valid.controller;

import com.summer.springboot.valid.entity.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/zip")
@Slf4j
public class ZipController {


    @GetMapping("/test")
    public ArrayList<Order> queryOrder() {
        ArrayList<Order> list = new ArrayList();
        for (int i = 0; i < 10000; i++) {
            Order order = new Order();
            order.setId(1L);
            order.notify();
            list.add(order);
        }
        return list;
    }
}
