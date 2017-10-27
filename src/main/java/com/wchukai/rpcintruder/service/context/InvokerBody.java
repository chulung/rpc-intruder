package com.wchukai.rpcintruder.service.context;

import com.wchukai.rpcintruder.service.RpcRequest;

public class InvokerBody {
    private final MethodInfo methodInfo;
    private final Object[] args;
    private final RpcRequest rpcRequest;

    public InvokerBody(MethodInfo methodInfo, Object[] args, RpcRequest rpcRequest) {
        this.methodInfo = methodInfo;
        this.args = args;
        this.rpcRequest = rpcRequest;
    }

    public MethodInfo getMethodInfo() {
        return methodInfo;
    }

    public Object[] getArgs() {
        return args;
    }

    public RpcRequest getRpcRequest() {
        return rpcRequest;
    }

    public Object invoke() throws Exception {
       return methodInfo.getMethod().invoke(methodInfo.getBeanInfo().getBean(), args);
    }
}
