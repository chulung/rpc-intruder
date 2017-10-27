package com.wchukai.rpcintruder.service.context;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wchukai.rpcintruder.service.RpcRequest;
import com.wchukai.rpcintruder.util.StringUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * @author chukai
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:rpc-inturder.service.xml")
public class InvocationContextTest {
    @Test
    public void getInstance() throws Exception {
        assertThat(InvocationContext.getInstance()).isNotNull();
    }

    @Test(expected = ClassNotFoundException.class)
    public void resolveBean() throws Exception {
        InvocationContext.getInstance().resolveBean("com.chu");
    }


    @Test
    public void getBeanInfoCache() throws Exception {
        assertThat(InvocationContext.getInstance().getBeanInfoCache()).isNotEmpty();
    }

    @Test
    public void setApplicationContext() throws Exception {

    }

    @Test
    public void onApplicationEvent() throws Exception {

    }

    @Test
    public void invoke() throws Exception {
        MethodInfo methodInfo = InvocationContext.getInstance().getBeanInfoCache().get(0).getMethodInfos().get(0);
        InvocationContext.getInstance().invoke(methodInfo.getId(), methodInfo.getArgsInfo());
    }

    @Test
    public void invoke1() throws Exception {
        MethodInfo methodInfo = InvocationContext.getInstance().getBeanInfoCache().get(0).getMethodInfos().get(0);
        RpcRequest rpcRequest = new RpcRequest();
        rpcRequest.setArgs(new ObjectMapper().readValue(methodInfo.getArgsInfo(), Object[].class));
        rpcRequest.setClassName(methodInfo.getBeanInfo().getInterfaceName());
        rpcRequest.setMethodName(methodInfo.getMethod().getName());
        rpcRequest.setArgsType(StringUtil.getTypeNames(methodInfo.getMethod().getParameterTypes()));
        InvocationContext.getInstance().invoke(rpcRequest);
    }

}