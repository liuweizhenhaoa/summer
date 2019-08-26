package com.summer.java.design.abtractfactory;

import com.summer.java.design.factory.Person;

public class PersonAbsFactory extends AbstractFactory {

    @Override
    public Person getPerson(String name) {
        return null;
    }

    @Override
    public Car getCar(String color) {
        return null;
    }
}
