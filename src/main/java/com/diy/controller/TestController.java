package com.diy.controller;


import com.diy.service.TestService;
import com.diy.service.TestService2;
import com.diy.service.TestService3;
import framework.annotations.MyAutoWired;
import framework.annotations.MyController;
import framework.annotations.MyRequestMapping;

@MyController
@MyRequestMapping("/test")
public class TestController {

    @MyAutoWired("testService1")
    private TestService service;

    @MyAutoWired("testServiceImpl2")
    private TestService service2;

    @MyAutoWired
    private TestService2 service3;

    @MyAutoWired("testService2")
    private TestService2 service4;

    @MyAutoWired("testServiceImpl3")
    private TestService3 service5;
    @MyAutoWired
    private TestController1 controller;

    @MyRequestMapping("tt")
    String test1() {
        return service2.test();
    }
}
