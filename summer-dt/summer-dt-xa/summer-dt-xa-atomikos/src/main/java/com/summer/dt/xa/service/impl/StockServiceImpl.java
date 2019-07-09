package com.summer.dt.xa.service.impl;

import com.summer.common.exception.BussinessException;
import com.summer.dt.xa.dao.stock.StockMapper;
import com.summer.dt.xa.entity.Stock;
import com.summer.dt.xa.service.StockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@Slf4j
public class StockServiceImpl implements StockService {


    @Autowired
    StockMapper stockMapper;

    @Override
    public void save(Stock stock) {
        log.info("--------------");
    }

    @Override
    public void reduceStock(long goodId, int reduceNum) {
        Stock stock = stockMapper.findStockByGoodId(goodId);

        checkStock(stock, goodId,reduceNum);

        //扣减库存
        stockMapper.update(stock.getNum()-reduceNum, goodId, new Date(), stock.getVersion(), stock.getVersion()+1);

    }

    public void checkStock(Stock stock, long goodId, int reduceNum){

        Optional.ofNullable(stock).orElseThrow(()-> new BussinessException("商品id为"+goodId+"不存在"));

        if(!(stock.getNum()>0 && stock.getNum()-reduceNum>=0)){
            throw new BussinessException("商品id为"+goodId+"库存不足");
        }
    }
}
