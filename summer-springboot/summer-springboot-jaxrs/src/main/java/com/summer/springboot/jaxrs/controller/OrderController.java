package com.summer.springboot.jaxrs.controller;

import com.summer.springboot.jaxrs.entity.Order;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "订单管理")
public class OrderController {


    @GetMapping("/test")
    @ApiOperation(value = "获取用户列表")
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
    @ApiOperation(value = "获取用户列表", notes = "根据id查询订单信息")
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
