package com.wchukai.rpcintruder.client;

import com.wchukai.service.BeanInitService;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Created by chulung on 2017/10/4.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:rpc-bean-init-checkenv.xml")
public class RpcServiceBeanInitTestCheckEnv {
    @Autowired
    private BeanInitService beanInitService;


    @BeforeClass
    public static void setUp() {
        System.setProperty("rpc-inturder", "enabled");
    }

    @Test
    public void beanInitTest() {
        assertThat(beanInitService).isNotNull();
    }
}