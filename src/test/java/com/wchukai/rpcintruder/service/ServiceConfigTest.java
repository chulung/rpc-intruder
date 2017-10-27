package com.wchukai.rpcintruder.service;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * @author chukai
 */
public class ServiceConfigTest {
    private ServiceConfiguration serviceConfig = new ServiceConfiguration();
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceConfigTest.class);

    @Before
    public void setUp() throws Exception {
        System.setProperty("rpc.intruder.enabled", "");
        serviceConfig.initProperties();
    }

    @Test
    public void getClassNames() throws Exception {
        assertThat(serviceConfig.getClassNames()).isNotEmpty();
    }


    @Test
    public void isEnable() throws Exception {
        serviceConfig.initProperties();
        assertThat(serviceConfig.isEnable()).isTrue();
    }

    @Test
    public void initProperties() throws Exception {

    }

    @Test
    public void getInvokeFilter() throws Exception {
        assertThat(serviceConfig.getInvokeFilter()).isNotNull();
    }

}