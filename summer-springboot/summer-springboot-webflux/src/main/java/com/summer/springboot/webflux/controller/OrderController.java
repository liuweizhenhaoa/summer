package com.summer.springboot.webflux.controller;

import com.summer.springboot.webflux.entity.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/valid")
@Slf4j
public class OrderController {


    @GetMapping("/test")
    public Map queryOrder(@Valid Order order, BindingResult result){
        if(result.hasErrors()){
            for (ObjectError error : result.getAllErrors()) {
                log.error(error.getDefaultMessage());
            }
        }
        log.info("--------------"+order.toString());
        return null;
    }

    @GetMapping("/test1")
    public Map queryOrder1(@Valid Order order){

        log.info("--------------"+order.toString());
        return null;
    }


    @GetMapping("/testVal")
    public void testVal(@PathVariable(value="pageNum")int pageNum, @PathVariable(value="pageSize")int pageSize){


    }

}
