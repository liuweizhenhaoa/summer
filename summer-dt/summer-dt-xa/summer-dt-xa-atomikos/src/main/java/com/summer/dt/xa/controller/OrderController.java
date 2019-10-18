package com.summer.dt.xa.controller;


import com.summer.common.exception.BussinessException;
import com.summer.dt.xa.entity.Order;
import com.summer.dt.xa.service.OrderService;
import com.summer.dt.xa.service.StockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;


/**
 */
@RestController
@RequestMapping("/api/order")
@Slf4j
public class OrderController {


    public static final String RESULT_SUCCESS = "success";
    public static final String RESULT_FAILED = "failed";

    @Autowired
    private OrderService orderService;

    @Autowired
    private StockService stockService;

    @GetMapping("/normal")
    @Transactional
    public String addIncome1() {

        try {
            Order order = new Order();
            order.setPrice(11.1d);
            order.setDetail("XA order tetail");
            order.setCreateTime(new Date());

            orderService.saveOrder(order);
            this.throwRuntimeException();

            stockService.reduceStock(1, 1);


            return RESULT_SUCCESS;
        } catch (Exception e) {
            log.error("error:", e);
            throw e;
        }
    }

    @GetMapping("/abnormal")
    @Transactional
    public String addIncome2() {
        try {
            Order order = new Order();
            order.setPrice(11.1d);
            order.setDetail("XA order tetail");
            order.setCreateTime(new Date());

            orderService.saveOrder(order);

            stockService.reduceStock(1, 1);

            return RESULT_SUCCESS;
        } catch (Exception e) {
            log.error("error:", e);
            throw e;
        }
    }

    public void throwRuntimeException() {
        throw new BussinessException("User defined exceptions");
    }
}
