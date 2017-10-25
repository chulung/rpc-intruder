package com.wchukai.rpcintruder.codec;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by wchukai on 2017/9/23.
 */
public interface Codec {
    byte[] doEncode(Object request) throws IOException;

    void doEncode(Object request, OutputStream outPut) throws IOException;

    Object doDecode(InputStream inputStream) throws IOException;
}
