package com.wchukai.service1.impl;

import com.wchukai.service1.HelloWorldService1;
import org.springframework.stereotype.Service;

/**
 * @author chukai
 */
@Service
public class HelloWorldService1Impl implements HelloWorldService1 {
    @Override
    public String hello(String name) {
        return name;
    }
}
