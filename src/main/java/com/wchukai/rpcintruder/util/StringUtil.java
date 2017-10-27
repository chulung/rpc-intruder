package com.wchukai.rpcintruder.util;

/**
 * @author chukai
 */
public class StringUtil {
    private StringUtil() {
    }

    public static boolean isNotEmpty(String s) {
        return !isEmpty(s);
    }

    public static boolean isEmpty(String s) {
        return s == null || s.isEmpty();
    }

    public static String[] getTypeNames(Class<?>[] parameterTypes) {
        String[] sa = new String[parameterTypes.length];
        for (int i = 0; i < parameterTypes.length; i++) {
            sa[i] = parameterTypes[i].getName();
        }
        return sa;
    }
}