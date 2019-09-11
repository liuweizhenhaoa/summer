package com.summer.spi.provider;

import com.summer.common.spi.SpiDemo;

public class SpiProviderTwo implements SpiDemo {

    @Override
    public String getSpi() {
        System.out.printf("SpiProviderTwo");
        return "two";
    }
}
