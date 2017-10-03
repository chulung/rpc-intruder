package com.wchukai.rpcintruder.codec;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * hessian编码器
 * Created by wenchukai on 2017/9/23.
 */
public class HessianCodec implements Codec {

    @Override
    public byte[] doEncode(Object request) throws IOException {
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        Hessian2Output output = new Hessian2Output(byteArray);
        output.writeObject(request);
        output.close();
        return byteArray.toByteArray();
    }

    @Override
    public void doEncode(Object request, OutputStream outPut) throws IOException {
        Hessian2Output objectOutput = new Hessian2Output(outPut);
        objectOutput.writeObject(request);
        objectOutput.close();
    }

    @Override
    public Object doDecode(byte[] bytes) throws IOException {
        Hessian2Input hessian2Input = new Hessian2Input(new ByteArrayInputStream(bytes));
        Object resultObject = hessian2Input.readObject();
        hessian2Input.close();
        return resultObject;
    }

}
