package com.summer.java.design.abtractfactory;

import com.summer.java.design.factory.Person;

public abstract class AbstractFactory {

    public abstract Person getPerson(String name);
    public abstract Car getCar(String color);
}
