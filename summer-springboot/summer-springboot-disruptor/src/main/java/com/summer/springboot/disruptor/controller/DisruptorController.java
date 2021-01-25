package com.summer.springboot.disruptor.controller;


import com.summer.springboot.disruptor.service.INotifyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liuwei
 */
@RestController
@RequestMapping("/api/disruptor")
@Slf4j
public class DisruptorController {

    @Autowired
    INotifyService notifyService;

    @GetMapping("test")
    @ResponseBody
    public String testLog() {
        log.info("=============");
        notifyService.sendNotify("Hello,World!");
        return "hello,world";
    }
}
