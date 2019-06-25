package com.summer.dt.service.impl;

import com.summer.dt.common.exception.BussinessException;
import com.summer.dt.dao.StockMapper;
import com.summer.dt.entity.Stock;
import com.summer.dt.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class StockServiceImpl implements StockService {


    @Autowired
    StockMapper stockMapper;

    @Override
    public void save(Stock stock) {
//        stockMapper.save(stock);
    }

    @Override
    public void reduceStock(long goodId, int reduceNum) {
        Stock stock = stockMapper.findStockByGoodId(goodId);

        checkStock(stock, goodId,reduceNum);

        //扣减库存
        stockMapper.update(stock.getNum()-reduceNum, goodId, new Date(), stock.getVersion());

    }

    public void checkStock(Stock stock, long goodId, int reduceNum){

        if(!Optional.of(stock).isPresent()){
            throw new BussinessException("商品id为"+goodId+"不存在");
        }

        if(!(stock.getNum()>0 && stock.getNum()-reduceNum>=0)){
            throw new BussinessException("商品id为"+goodId+"库存不足");
        }
    }
}
