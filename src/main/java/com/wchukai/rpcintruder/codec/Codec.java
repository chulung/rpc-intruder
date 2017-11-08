package com.wchukai.rpcintruder.codec;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * @author wchukai
 */
public interface Codec {
    /**
     *  encode the object and write to output.
     * @param object the object
     * @param outPut the output stream
     * @throws IOException io exception
     */
    void doEncode(Object object, OutputStream outPut) throws IOException;

    /**
     * decode an object from input stream
     * @param inputStream the input stream
     * @return the object
     * @throws IOException io exception
     */
    Object doDecode(InputStream inputStream) throws IOException;
}
