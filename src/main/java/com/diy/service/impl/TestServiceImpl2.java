package com.diy.service.impl;


import com.diy.service.TestService;
import framework.annotations.MyService;

@MyService
public class TestServiceImpl2 implements TestService {
    @Override
    public String test() {
        String result = "i am test " + this.getClass().getSimpleName();
        System.out.println(result);
        return result;
    }
}