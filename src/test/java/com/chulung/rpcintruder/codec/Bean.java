package com.chulung.rpcintruder.codec;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Created by chulung on 2017/9/23.
 */
public class Bean extends AbstractBean implements Serializable {
    private BigInteger f;

    public BigInteger getF() {
        return f;
    }

    public void setF(BigInteger f) {
        this.f = f;
    }
}
