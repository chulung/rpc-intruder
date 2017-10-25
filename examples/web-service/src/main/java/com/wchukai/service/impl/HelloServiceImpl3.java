package com.wchukai.service.impl;

import com.wchukai.model.Hello;
import com.wchukai.model.World;
import com.wchukai.service.HelloService3;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author chukai
 * @date 2017/10/23
 */
@Service
public class HelloServiceImpl3 implements HelloService3 {
    @Override
    public List<Serializable> invokeHello(int num, Hello hello, World world) {
        return Arrays.asList(num, hello, world);
    }

    @Override
    public List<Serializable> invokeHello2(int num, Hello hello, World world) {
        return null;
    }

    @Override
    public List<Serializable> invokeHello3(int num, Hello hello, World world) {
        return null;
    }
}
