package com.chulung.rpcintruder.client;

import com.chulung.rpcintruder.servlet.RpcRequest;
import com.chulung.rpcintruder.codec.Codec;
import com.chulung.rpcintruder.codec.HessianCodec;
import com.chulung.rpcintruder.servlet.RpcResponse;
import org.apache.commons.io.IOUtils;
import org.springframework.cglib.proxy.*;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.*;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by wenchukai on 2017/9/8.
 */

/**
 * service代理，代替已有的RPC服务发起请求
 */
public class ServiceProxy implements MethodInterceptor {
    private ConfigurableApplicationContext applicationContext;
    private Codec codec = new HessianCodec();
    /**
     * 忽略object的方法
     */
    public Set<Method> IgnoreMethods = new HashSet() {
        {
            for (Method method : Object.class.getMethods())
                add(method);
        }
    };


    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        if (IgnoreMethods.contains(method)) {
            return methodProxy.invokeSuper(o, objects);
        }
        RpcRequest rpcRequest = new RpcRequest();
        rpcRequest.setClassName(o.getClass().getName().split("\\$\\$")[0]);
        rpcRequest.setMethodName(method.getName());
        rpcRequest.setArgs(objects);
        RpcResponse rpcResponse = doRequest(rpcRequest);
        if (rpcResponse.getException() != null) {
            throw rpcResponse.getException();
        }
        return rpcResponse.getResponse();
    }

    private RpcResponse doRequest(RpcRequest rpcRequest) throws Exception {
        String url = "http://rpc.trade.yhd.com/rpc";
        HttpURLConnection httpConn = null;
        URL urlObj = new URL(url);
        httpConn = (HttpURLConnection) urlObj.openConnection();
        int timeout = 30000;
        httpConn.setReadTimeout(timeout);
        httpConn.setConnectTimeout(timeout);
        httpConn.setDoOutput(true);
        httpConn.setRequestMethod("POST");
        httpConn.getOutputStream().write(codec.doEncode(rpcRequest));
        httpConn.getOutputStream().flush();
        return getResponse(httpConn);
    }

    private RpcResponse getResponse(HttpURLConnection httpConn) throws Exception {
        int code = 500;
        try {
            code = httpConn.getResponseCode();
        } catch (Exception e) {
            throw new Exception("getResponseCode  exception ", e);
        }
        InputStream is = null;
        if (code != 200) {
            String content = null;
            try {
                is = httpConn.getInputStream();
                if (is != null) {
                    content = IOUtils.toString(is);
                }
                is = httpConn.getErrorStream();
                if (is != null) {
                    content = IOUtils.toString(is);
                }
            } catch (FileNotFoundException e) {
                throw new Exception(String.valueOf(e));
            } catch (IOException e) {
                if (is == null)
                    throw new Exception(code + ": " + e, e);
            }
            if (is != null) {
                IOUtils.closeQuietly(is);
            }
            throw new Exception(code + ": " + content);
        }
        try {
            is = httpConn.getInputStream();
        } catch (IOException e) {
            if (is == null)
                throw new Exception(code + ": " + e, e);
        }
        try {
            return (RpcResponse) codec.doDecode(IOUtils.toByteArray(is));
        } catch (IOException e) {
            throw new Exception("read InputStream exception ", e);
        } finally {
            if (is != null) {
                IOUtils.closeQuietly(is);
            }
        }
    }

}
