package com.summer.agent.server;

import com.summer.common.spi.SpiDemo;


public class SpiProviderOne implements SpiDemo {
    @Override
    public String getSpi() {
        System.out.printf("SpiProviderOne");
        return "one";
    }
}
