package com.wchukai.rpcintruder.client;

import com.wchukai.service.HelloWorldServie;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by wchukai on 2017/9/23.
 */
public class ServiceFactoryBeanTest {
    @Test
    public void createProxy() throws Exception {
        ServiceFactoryBean serviceFactoryBean = new ServiceFactoryBean();
        serviceFactoryBean.setServiceClass(HelloWorldServie.class);
        serviceFactoryBean.setServiceProxy(new ServiceProxy());
        HelloWorldServie helloWorldServie = (HelloWorldServie) serviceFactoryBean.getObject();
        assertThat(helloWorldServie.getClass().getName().split("\\$\\$")[0]).isEqualTo(HelloWorldServie.class.getName());
    }
}