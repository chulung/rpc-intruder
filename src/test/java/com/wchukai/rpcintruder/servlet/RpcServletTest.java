package com.wchukai.rpcintruder.servlet;

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
@ContextConfiguration(locations = "classpath:rpc-bean-init.xml")
public class RpcServletTest extends AbstractServlet {
    @Autowired
    private RpcServlet rpcServlet;

    @Test
    public void applicationContextTest() {
        assertThat(applicationContext).isNotNull();
        assertThat(rpcServlet).hasFieldOrPropertyWithValue("applicationContext", applicationContext);
    }
}