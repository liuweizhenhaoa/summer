/*
 * @(#) AbstractControllerTest.java 2020-07-29
 *
 * Copyright 2020 NetEase.com, Inc. All rights reserved.
 */

package com.summer.grpc.consumer.tp.service;

import com.summer.grpc.consumer.GrpcConsumerApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author liuwei
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GrpcConsumerApplication.class)
@Slf4j
public abstract class AbstractControllerTest implements InitializingBean {

}
