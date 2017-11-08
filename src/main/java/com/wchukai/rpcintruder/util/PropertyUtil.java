package com.wchukai.rpcintruder.util;

import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author chukai
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
