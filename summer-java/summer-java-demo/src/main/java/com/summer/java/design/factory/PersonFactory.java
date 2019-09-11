package com.summer.java.design.factory;


public class PersonFactory {

    public Person getPerson(String name) {
        if (name == null) {
            return null;
        }
        if (name.equalsIgnoreCase("man")) {
            return new Man();
        }else if (name.equalsIgnoreCase("woman")) {
            return new Woman();
        }
        return null;
    }
}
