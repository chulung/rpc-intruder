package com.wchukai.rpcintruder.service.exception;

/**
 * 方法调用被强制中断时抛出此异常
 *
 * @author chukai
 */
public class InvokeTerminatedException extends RuntimeException {
    public InvokeTerminatedException(String message) {
        super(message);
    }
}
