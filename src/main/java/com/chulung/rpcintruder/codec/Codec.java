package com.chulung.rpcintruder.codec;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by wenchukai on 2017/9/23.
 */
public interface Codec {
    byte[] doEncode(Object request) throws IOException;

    void doEncode(Object request, OutputStream outPut) throws IOException;

    Object doDecode(byte[] bytes) throws IOException;
}
