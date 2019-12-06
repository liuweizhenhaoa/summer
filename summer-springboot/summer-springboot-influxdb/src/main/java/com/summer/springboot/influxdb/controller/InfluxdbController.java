package com.summer.springboot.influxdb.controller;

import com.summer.springboot.influxdb.entity.Cpu;
import com.summer.springboot.influxdb.entity.Order;
import com.summer.springboot.influxdb.service.InfluxService;
import lombok.extern.slf4j.Slf4j;
import org.influxdb.dto.QueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/influxdb")
@Slf4j
public class InfluxdbController {

    @Autowired
    InfluxService influxService;


    @GetMapping("/query/{id}")
    public List<Cpu> queryOrder(@PathVariable(value = "id") int id) {
        List<Cpu> cpus = influxService.querySql("select * from order where id="+id);
        log.info("--------------{}" , cpus);
        return null;
    }

    @GetMapping("/createDb")
    public Map queryOrder1(@Valid Order order) {

        log.info("--------------" + order.toString());
        return null;
    }


    @PostMapping("/insert")
    public void testVal(@Valid Cpu cpu) {
        influxService.insertCpu(cpu);
    }

}
