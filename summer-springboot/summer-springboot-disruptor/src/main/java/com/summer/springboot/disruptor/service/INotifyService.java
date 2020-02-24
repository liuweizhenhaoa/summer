package com.summer.springboot.disruptor.service;

public interface INotifyService {

    public void sendNotify(String message);

    void consume();
}
