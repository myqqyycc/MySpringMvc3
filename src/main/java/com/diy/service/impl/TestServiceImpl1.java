package com.diy.service.impl;

import com.diy.service.TestService;
import framework.annotations.MyService;

@MyService("testService1")
public class TestServiceImpl1 implements TestService {
    @Override
    public String test() {
        String result = "i am test " + this.getClass().getSimpleName();
        System.out.println(result);
        return result;
    }
}
