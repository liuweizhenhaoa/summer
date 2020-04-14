package com.summer.springboot.groovy.shell.model

class Man {
    Long id
    String name
    String phone

    Man(Long id, String name, String phone) {
        this.id = id
        this.name = name
        this.phone = phone
    }
    Man() {
    }

    static void main(String[] args) {
        def m = new Man();
        m.id = 1;
        m.name = "111";
        println(m.toString())
    }

}
