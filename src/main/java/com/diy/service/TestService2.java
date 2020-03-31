package com.diy.service;


import framework.annotations.MyService;

@MyService
public class TestService2 {

    public String test2() {
        String result = "i am test " + this.getClass().getSimpleName();
        System.out.println(result);
        return result;
    }

}
