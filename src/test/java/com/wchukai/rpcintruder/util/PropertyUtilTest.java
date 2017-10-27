package com.wchukai.rpcintruder.util;

import org.junit.Test;

import java.util.Properties;

import static org.assertj.core.api.Java6Assertions.assertThat;


/**
 * Created by chukai on 2017/10/25.
 */
public class PropertyUtilTest {

    @Test
    public void testLoadFile() {
        Properties properties = PropertyUtil.loadFile("/rpc.intruder.properties");
        assertThat(properties.get("rpc.intruder.preload.interface")).isNotNull();
        assertThat(properties.get("rpc.intruder.preload.package")).isNotNull();
        properties = PropertyUtil.loadFile("/a.properties");
        assertThat(properties).isEmpty();
    }
}