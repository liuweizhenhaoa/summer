package com.summer.springboot.influxdb.service;
import java.time.Instant;
import	java.util.Random;


import com.summer.springboot.influxdb.InfluxdbApplication;
import com.summer.springboot.influxdb.entity.Cpu;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = InfluxdbApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class InfluxServiceTest {

    @Autowired
    InfluxService influxService;


    @Before
    public void setUp() {
    }

    @After
    public  void tearDown() {
    }

    @Test
    public  void querySql() {
        influxService.querySql("SELECT * FROM cpu ");
    }

    @Test
    public void queryByParam() {
        influxService.queryByParam("");
    }

    @Test
    public void querySqlUseCallBack() {
    }

    @Test
    public void createInfluxDatabase() {
    }

    @Test
    public void createInfluxRetentionPolicy() {
        influxService.createInfluxRetentionPolicy("aRetentionPolicy");

    }

    @Test
    public void writeRecord() {

    }

    @Test
    public void insertCpu() {

        Random random = new Random();

        Cpu cpu = new Cpu().setHappydevop(random.nextBoolean())
                .setHostname("server"+random.nextInt(10))
                .setIdle(random.nextDouble())
                .setRegion("china")
                .setTime(Instant.now())
                .setUptimeSecs(System.currentTimeMillis());

        influxService.insertCpu(cpu);
    }
}