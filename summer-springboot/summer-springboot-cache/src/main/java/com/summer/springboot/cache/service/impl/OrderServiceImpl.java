package com.summer.springboot.cache.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.summer.common.idgenerate.SnowflakeIdWorker;
import com.summer.springboot.cache.dao.OrderMapper;
import com.summer.springboot.cache.entity.Order;
import com.summer.springboot.cache.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@CacheConfig(cacheNames = "summer:springboot:cache:order")
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    public static final String CACHE_KEY_ORDE = "";

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    SnowflakeIdWorker snowflakeIdWorker;

    @CacheEvict(key =  "#order.id")
    @Override
    public Order saveOrder(Order order) {
        order.setId(snowflakeIdWorker.nextId());
        orderMapper.save(order);
        return order;
    }

    @CachePut(key = "#order.id")
    @Override
    public void updateOrder(Order order) {
        orderMapper.update(order);
    }

    @CacheEvict(key = "#p0")
    @Override
    public void deleteOrder(long id) {
        orderMapper.delete(id);
    }

    @Cacheable(key = "#p0")
    @Override
    public Order queryOrder(long id) {
        log.info("-----------------");
        return orderMapper.findOrderById(id);
    }

    @Override
    public List<Order> queryAll(int pageNum,int pageSize) {
        Page<Order> page  = PageHelper.startPage(pageNum, pageSize);
        return orderMapper.queryAll();
    }


}
