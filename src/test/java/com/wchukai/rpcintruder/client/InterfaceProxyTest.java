package com.wchukai.rpcintruder.client;

import com.wchukai.service0.HelloWorldService0;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.lang.reflect.Method;

/**
 * @author chukai
 */
public class InterfaceProxyTest {
    @Test(expected = Exception.class)
    public void intercept() throws Throwable {
        InterfaceProxyFactoryBean interfaceProxyFactoryBean = new InterfaceProxyFactoryBean();
        InterfaceProxy serviceProxy = new InterfaceProxy();
        serviceProxy.setUrl("http://0.0.0.0");
        interfaceProxyFactoryBean.setServiceProxy(serviceProxy);
        HelloWorldService0 service0 = interfaceProxyFactoryBean.createProxy(HelloWorldService0.class);
        service0.hello("");
    }

}