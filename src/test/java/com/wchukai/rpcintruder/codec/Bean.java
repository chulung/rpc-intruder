package com.wchukai.rpcintruder.codec;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * Created by wchukai on 2017/9/23.
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
