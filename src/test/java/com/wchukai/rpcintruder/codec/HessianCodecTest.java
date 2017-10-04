package com.wchukai.rpcintruder.codec;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
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
        byte[] bytes = codec.doEncode(bean);
        Object actual = codec.doDecode(new ByteArrayInputStream(bytes));
        assertThat(actual).isEqualToComparingFieldByField(bean);
    }


}