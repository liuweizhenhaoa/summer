package com.summer.dt.mq.service;


import com.summer.dt.mq.entity.Stock;

public interface StockService {
    void save(Stock stock);

    void reduceStock(long goodId,int reduceNum);
}
