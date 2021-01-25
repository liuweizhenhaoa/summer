package com.summer.springboot.disruptor.service;

public interface INotifyService {

    void sendNotify(String message);

    void consume();
}
