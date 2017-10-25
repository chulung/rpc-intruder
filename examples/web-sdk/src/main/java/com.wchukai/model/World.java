package com.wchukai.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author chukai
 * @date 2017/10/23
 */
public class World implements Serializable{
    private BigDecimal bigDecimal;
    private Hello hello;

    public BigDecimal getBigDecimal() {
        return bigDecimal;
    }

    public void setBigDecimal(BigDecimal bigDecimal) {
        this.bigDecimal = bigDecimal;
    }

    public Hello getHello() {
        return hello;
    }

    public void setHello(Hello hello) {
        this.hello = hello;
    }
}
