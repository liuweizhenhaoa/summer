package com.summer.springboot.influxdb.controller;

import com.summer.springboot.influxdb.entity.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/valid")
@Slf4j
public class OrderController {


    @GetMapping("/test")
    public Map queryOrder(@Valid Order order, BindingResult result) {
        if (result.hasErrors()) {
            for (ObjectError error : result.getAllErrors()) {
                log.error(error.getDefaultMessage());
            }
        }
        log.info("--------------" + order.toString());
        return null;
    }

    @GetMapping("/test1")
    public Map queryOrder1(@Valid Order order) {

        log.info("--------------" + order.toString());
        return null;
    }


    @GetMapping("/testVal")
    public void testVal(@PathVariable(value = "pageNum") int pageNum, @PathVariable(value = "pageSize") int pageSize) {


    }

    @GetMapping("/test2")
    public void testVal1(int pageNum, int pageSize) {


    }

}
