package com.summer.spi.consumer;

import com.summer.common.spi.SpiDemo;
import com.summer.spi.provider.SpiProviderOne;

import java.sql.Driver;
import java.util.Iterator;
import java.util.Optional;
import java.util.ServiceLoader;
import java.util.stream.StreamSupport;

public class SpiConsumer {

    public static void main(String[] args) {
       SpiConsumer spiConsumer = new SpiConsumer();
       spiConsumer.getSpiDemo().getSpi();
    }


    public SpiDemo getSpiDemo(){
        ServiceLoader<SpiDemo> spiDemos = ServiceLoader.load(SpiDemo.class);
        final Optional<SpiDemo> spiDemo = StreamSupport.stream(spiDemos.spliterator(), false)
                .findFirst();

//        Iterator<SpiDemo> driversIterator = spiDemos.iterator();
//
//        /* Load these drivers, so that they can be instantiated.
//         * It may be the case that the driver class may not be there
//         * i.e. there may be a packaged driver with the service class
//         * as implementation of java.sql.Driver but the actual class
//         * may be missing. In that case a java.util.ServiceConfigurationError
//         * will be thrown at runtime by the VM trying to locate
//         * and load the service.
//         *
//         * Adding a try catch block to catch those runtime errors
//         * if driver not available in classpath but it's
//         * packaged as service and that service is there in classpath.
//         */
//        try{
//            while(driversIterator.hasNext()) {
//                driversIterator.next();
//            }
//        } catch(Throwable t) {
//            // Do nothing
//        }
        return spiDemo.orElse(new SpiProviderOne());
//        return null;

    }
}
