package com.wchukai.rpcintruder.client;

import com.wchukai.service0.HelloWorldService0;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by wchukai on 2017/9/23.
 */
public class InterfaceProxyFactoryBeanTest {
    @Test
    public void createProxy() throws Exception {
        InterfaceProxyFactoryBean serviceFactoryBean = new InterfaceProxyFactoryBean();
        serviceFactoryBean.setServiceClass(HelloWorldService0.class);
        serviceFactoryBean.setServiceProxy(new InterfaceProxy());
        HelloWorldService0 helloWorldServie = (HelloWorldService0) serviceFactoryBean.getObject();
        assertThat(helloWorldServie.getClass().getName().split("\\$\\$")[0]).isEqualTo(HelloWorldService0.class.getName());
    }
}