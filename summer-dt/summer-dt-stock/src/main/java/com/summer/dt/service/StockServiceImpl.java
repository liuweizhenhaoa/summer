package com.summer.dt.service;

import com.summer.dt.dao.StockMapper;
import com.summer.dt.entity.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockServiceImpl implements StockService{


    @Autowired
    StockMapper stockMapper;

    @Override
    public void save(Stock stock) {
        stockMapper.save(stock);
    }
}
