package com.wchukai.rpcintruder.client;

import com.wchukai.rpcintruder.codec.Codec;
import com.wchukai.rpcintruder.codec.HessianCodec;
import com.wchukai.rpcintruder.service.RpcRequest;
import com.wchukai.rpcintruder.service.RpcResponse;
import com.wchukai.rpcintruder.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.*;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by wchukai on 2017/9/8.
 */

/**
 * service代理，代替已有的RPC服务发起请求
 *
 * @author chukai
 */
public class InterfaceProxy implements MethodInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(InterfaceProxy.class);
    public static final int HTTP_STATU_OK = 200;
    public static final int DEFAULT_TIMEOUT = 30000;
    private ConfigurableApplicationContext applicationContext;

    private Codec codec = new HessianCodec();
    /**
     * 忽略object的方法
     */
    public Set<Method> ignoreMethods = new HashSet() {
        {
            for (Method method : Object.class.getMethods()) {
                add(method);
            }
        }
    };
    private String url;


    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        if (ignoreMethods.contains(method)) {
            return methodProxy.invokeSuper(o, objects);
        }
        RpcRequest rpcRequest = new RpcRequest();
        rpcRequest.setClassName(o.getClass().getName().split("\\$\\$")[0]);
        rpcRequest.setMethodName(method.getName());
        rpcRequest.setArgs(objects);
        rpcRequest.setArgsType(StringUtil.getTypeNames(method.getParameterTypes()));
        InetAddress addr = InetAddress.getLocalHost();
        rpcRequest.setHostName(addr.getHostName());
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("rpc-intruder:url:{},class:{},method:{}", new Object[]{url, rpcRequest.getClassName(), rpcRequest.getMethodName()});
        }
        RpcResponse rpcResponse = doRequest(rpcRequest);
        if (rpcResponse.getException() != null) {
            throw rpcResponse.getException();
        }
        return rpcResponse.getResponse();
    }


    private RpcResponse doRequest(RpcRequest rpcRequest) throws Exception {
        HttpURLConnection httpConn = (HttpURLConnection) new URL(url).openConnection();
        int timeout = DEFAULT_TIMEOUT;
        httpConn.setReadTimeout(timeout);
        httpConn.setConnectTimeout(timeout);
        httpConn.setDoOutput(true);
        httpConn.setRequestMethod("POST");
        codec.doEncode(rpcRequest, httpConn.getOutputStream());
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
        if (code != HTTP_STATU_OK) {
            String content = null;
            try {
                is = httpConn.getInputStream();
                if (is != null) {
                    content = convertStreamToString(is);
                }
                is = httpConn.getErrorStream();
                if (is != null) {
                    content = convertStreamToString(is);
                }
            } catch (FileNotFoundException e) {
                throw new Exception("please check the url.\n" + String.valueOf(e));
            } catch (IOException e) {
                if (is == null) {
                    throw new Exception(code + ": " + e, e);
                }
            }
            throw new Exception(code + ": " + content);
        }
        try {
            return (RpcResponse) codec.doDecode(httpConn.getInputStream());
        } catch (IOException e) {
            throw new Exception("read InputStream exception ", e);
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static String convertStreamToString(InputStream is) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line + "\n");
        }

        return sb.toString();
    }
}
