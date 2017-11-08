package com.wchukai.rpcintruder.service;

import com.wchukai.rpcintruder.codec.Codec;
import com.wchukai.rpcintruder.codec.HessianCodec;
import com.wchukai.rpcintruder.service.action.*;
import com.wchukai.rpcintruder.service.context.InvocationContext;
import com.wchukai.rpcintruder.service.exception.InvokeTerminatedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author chukai
 */
public class ServiceServlet extends HttpServlet {
    private static Logger logger = LoggerFactory.getLogger(ServiceServlet.class);
    private static final Map<String, AbstractAction> ACTIONS = new HashMap() {
        {
            put("asset", new AssetsAction());
            put("index", new IndexAction());
            put("invoke", new InvokeAction());
            put("reg", new RegisterBeanAction());
            put("beanInfo", new BeanInfoAction());
        }
    };
    public Map<String, Method> map = new ConcurrentHashMap<>();
    private Codec codec = new HessianCodec();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!InvocationContext.getInstance().isEnabled()) {
            try (PrintWriter out = response.getWriter()) {
                response.setStatus(200);
                response.setContentType("text/html");
                out.println("<h1>RPC Intruder disabled</h1>");
            }
            return;
        }
        String act = request.getParameter("action");
        if (act != null && ACTIONS.get(act) != null) {
            ACTIONS.get(act).execute(request, response);
            return;
        }
        try (PrintWriter out = response.getWriter()) {
            response.setStatus(200);
            response.setContentType("text/html");
            out.println("<h1>RPC Intruder enabled!</h1>");
        }

    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RpcResponse rpcResponse = new RpcResponse();
        OutputStream out = response.getOutputStream();
        try {
            if (!InvocationContext.getInstance().isEnabled()) {
                throw new InvokeTerminatedException("RPC Intruder disabled");
            }
            response.setContentType("x-application/object");
            byte[] rpcResponseBytes = null;
            RpcRequest rpcRequest = getRpcRequest(request);
            Object rs = InvocationContext.getInstance().invoke(rpcRequest);
            rpcResponse.setResponse(rs);
            codec.doEncode(rpcResponse, out);
        } catch (Exception e) {
            rpcResponse.setException(e.getCause() != null ? e.getCause() : e);
            codec.doEncode(rpcResponse, out);
        } finally {
            out.close();
        }

    }

    public RpcRequest getRpcRequest(HttpServletRequest request) throws Exception {
        return (RpcRequest) codec.doDecode(request.getInputStream());
    }


}
