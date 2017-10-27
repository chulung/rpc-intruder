package com.wchukai.rpcintruder.client;

import com.wchukai.service0.HelloWorldService0;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author chukai
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:rpc-inturder.client.xml")
public class ClientConfigurationTest {
    @Autowired
    private HelloWorldService0 helloWorldServie0;

    @Test
    public void postProcessBeanDefinitionRegistry() throws Exception {
        assertThat(helloWorldServie0.getClass().getName()).contains("EnhancerByCGLIB");
    }

}