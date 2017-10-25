package com.wchukai.service;

import com.wchukai.model.Hello;
import com.wchukai.model.World;

import java.io.Serializable;
import java.util.List;

/**
 * Created by chukai on 2017/10/23.
 */
public interface HelloService3 {
    List<Serializable> invokeHello(int num, Hello hello, World world);
    List<Serializable> invokeHello2(int num, Hello hello, World world);
    List<Serializable> invokeHello3(int num, Hello hello, World world);
}
