package com.wchukai.rpcintruder.codec;

import com.caucho.hessian.io.Hessian2Output;
import com.wchukai.rpcintruder.service.RpcRequest;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by wchukai on 2017/9/23.
 */
public class HessianCodecTest {
    private Bean bean = new Bean();
    private Codec codec = new HessianCodec();

    @Before
    public void setUp() {
        bean.setA(1);
        bean.setB(BigDecimal.ONE);
        bean.setC("c");
        bean.setD(true);
        bean.setF(BigInteger.ONE);
    }

    @Test
    public void doEncode() throws Exception {
        RpcRequest rpcRequest = new RpcRequest();
        rpcRequest.setClassName("com.wchukai.service0.HelloWorldService0");
        rpcRequest.setMethodName("hello");
        rpcRequest.setArgs(new Object[]{""});

        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        Hessian2Output output = new Hessian2Output(byteArray);
        output.writeObject(rpcRequest);
        output.close();

    }


}