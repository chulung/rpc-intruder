package com.wchukai.service0.impl;

import com.wchukai.service0.HelloWorldService0;
import org.springframework.stereotype.Service;

/**
 * @author chukai
 */
@Service
public class HelloWorldService0Impl implements HelloWorldService0 {
    @Override
    public String hello(String name) {
        return name;
    }
}
