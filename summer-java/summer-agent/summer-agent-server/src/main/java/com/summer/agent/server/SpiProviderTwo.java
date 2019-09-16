package com.summer.agent.server;

import com.summer.common.spi.SpiDemo;

public class SpiProviderTwo implements SpiDemo {

    @Override
    public String getSpi() {
        System.out.printf("SpiProviderTwo");
        return "two";
    }
}
