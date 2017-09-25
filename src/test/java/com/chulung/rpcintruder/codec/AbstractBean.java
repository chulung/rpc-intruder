package com.chulung.rpcintruder.codec;

import java.math.BigDecimal;

/**
 * Created by chulung on 2017/9/23.
 */
public class AbstractBean {
    private int a;
    private BigDecimal b;
    private String c;
    private boolean d;

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public BigDecimal getB() {
        return b;
    }

    public void setB(BigDecimal b) {
        this.b = b;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    public boolean isD() {
        return d;
    }

    public void setD(boolean d) {
        this.d = d;
    }

    public boolean getD() {
        return d;
    }
}
