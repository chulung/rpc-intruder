package com.wchukai.rpcintruder.service;

import com.wchukai.rpcintruder.codec.Codec;
import com.wchukai.rpcintruder.util.StringUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static org.powermock.api.mockito.PowerMockito.*;

/**
 * Created by chulung on 2017/10/4.
 */
@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:rpc-inturder.service.xml")
public class ServiceServletTest {
    private ServiceServlet serviceServlet = new ServiceServlet();
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private PrintWriter printWriter;

    @Mock
    private ServletOutputStream out;

    @Before
    public void setUp() throws IOException {
        System.setProperty("rpc.intruder","enabled");
        when(response.getWriter()).thenReturn(printWriter);
        when(response.getOutputStream()).thenReturn(out);
        serviceServlet = PowerMockito.spy(serviceServlet);
    }

    @Test
    public void doGetTest() throws ServletException, IOException {
        serviceServlet.doGet(request, response);
    }

    @Test
    public void doGetTestIndex() throws ServletException, IOException {

        when(request.getParameter("action")).thenReturn("asset");
        serviceServlet.doGet(request, response);

        when(request.getParameter("action")).thenReturn("index");
        serviceServlet.doGet(request, response);
    }

    @Test
    public void doGetTestInvoke() throws ServletException, IOException {
        when(request.getParameter("id")).thenReturn("1");
        when(request.getParameter("args")).thenReturn("[null]");
        when(request.getParameter("action")).thenReturn("invoke");
        serviceServlet.doGet(request, response);
    }

    @Test
    public void doGetTestReg() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn("reg");
        when(request.getParameter("className")).thenReturn("com.wchukai.service0.HelloWorldService0");
        serviceServlet.doGet(request, response);

        when(request.getParameter("action")).thenReturn("beanInfo");
        serviceServlet.doGet(request, response);
    }

    @Test
    public void doPost() throws Exception {
        ReflectionTestUtils.setField(serviceServlet, "codec", mock(Codec.class));
        RpcRequest rpcRequest = new RpcRequest();
        rpcRequest.setClassName("com.wchukai.service0.HelloWorldService0");
        rpcRequest.setMethodName("hello");
        rpcRequest.setArgs(new Object[]{""});
        rpcRequest.setArgsType(StringUtil.getTypeNames(new Class[]{String.class}));
        doReturn(rpcRequest).when(serviceServlet).getRpcRequest(request);
        serviceServlet.doPost(request, response);
    }
}