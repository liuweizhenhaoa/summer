package com.summer.dt.xa.service;


import com.summer.dt.xa.entity.Stock;

public interface StockService {
    void save(Stock stock);

    void reduceStock(long goodId, int reduceNum);
}
