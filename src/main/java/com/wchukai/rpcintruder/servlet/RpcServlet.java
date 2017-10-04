package com.wchukai.rpcintruder.servlet;

import com.wchukai.rpcintruder.codec.Codec;
import com.wchukai.rpcintruder.codec.HessianCodec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by wenchukai on 2017/9/8.
 */
@Component
public class RpcServlet extends AbstractServlet {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public Map<String, Method> map = new ConcurrentHashMap<>();
    private Codec codec = new HessianCodec();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setStatus(200); // , "RPC Requires POST");
        PrintWriter out = resp.getWriter();
        resp.setContentType("text/html");
        out.println("<h1>Hello,RPC Intruder!</h1>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        RpcResponse rpcResponse = new RpcResponse();
        response.setContentType("x-application/object");
        byte[] rpcResponseBytes = null;
        try {
            if (applicationContext == null) {
                throw new IllegalArgumentException("ApplicationContext is null,please configure <context:component-scan base-package=\"com.wchukai.rpcintruder\">.");
            }
            RpcRequest rpcRequest = getRpcRequest(request);
            String serviceFullName = rpcRequest.getClassName();
            Class<?> serviceClass = Class.forName(serviceFullName);
            Object service = applicationContext.getBean(serviceClass);
            if (service == null) {
                throw new NoSuchBeanDefinitionException(serviceClass);
            }
            Method method = getMethod(rpcRequest, serviceClass);
            rpcResponse.setResponse(method.invoke(service, rpcRequest.getArgs()));
            rpcResponseBytes = codec.doEncode(rpcResponse);
        } catch (Exception e) {
            logger.error("", e);
            rpcResponse.setException(e);
            rpcResponseBytes = codec.doEncode(response);
        }
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(rpcResponseBytes);
        outputStream.close();
    }

    private Method getMethod(RpcRequest rpcRequest, Class<?> serviceClass) throws NoSuchMethodException {
        String methodName = rpcRequest.getClassName() + "." + rpcRequest.getMethodName();
        Method method = this.map.get(methodName);
        if (method == null) {
            initMethod(serviceClass, rpcRequest.getClassName(), rpcRequest.getMethodName());
        }
        method = this.map.get(methodName);
        if (method == null) {
            throw new NoSuchMethodException(methodName);
        }
        return method;
    }

    private RpcRequest getRpcRequest(HttpServletRequest request) throws Exception {
        return (RpcRequest) codec.doDecode(request.getInputStream());
    }

    private void initMethod(Class service, String serviceFullName, String methodName) {
        for (Method m : service.getMethods()) {
            if (m.getName().equals(methodName)) {
                this.map.put(serviceFullName + '.' + methodName, m);
            }
        }
    }


}
