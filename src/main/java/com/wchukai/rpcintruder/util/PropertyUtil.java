package com.wchukai.rpcintruder.util;

import java.io.InputStream;
import java.util.Properties;

/**
 * Created by chukai on 2017/10/25.
 */
public class PropertyUtil {

    public static Properties loadFile(String path) {
        Properties props = new Properties();
        try (InputStream in = PropertyUtil.class.getResourceAsStream(path)) {
            props.load(in);
        } catch (Exception e) {

        }
        return props;
    }
}
