package com.summer.dt.service;


import com.summer.dt.entity.Stock;

public interface StockService {
    void save(Stock stock);

    void reduceStock(long goodId,int reduceNum);
}
