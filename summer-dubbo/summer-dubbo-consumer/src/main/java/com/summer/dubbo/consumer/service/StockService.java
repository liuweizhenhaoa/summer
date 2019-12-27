package com.summer.dubbo.consumer.service;


import com.summer.dubbo.consumer.entity.Stock;

public interface StockService {
    void save(Stock stock);

    void reduceStock(long goodId,int reduceNum);

    Stock getStockById(int id);
}
