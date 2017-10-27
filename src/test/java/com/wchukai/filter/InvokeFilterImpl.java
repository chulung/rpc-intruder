package com.wchukai.filter;

import com.wchukai.rpcintruder.service.context.InvokeFilter;
import com.wchukai.rpcintruder.service.context.InvokerBody;

/**
 * @author chukai
 */
public class InvokeFilterImpl extends InvokeFilter {
    @Override
    public Object invoke(InvokerBody invokerBody) throws Exception {
        return invokerBody.invoke();
    }
}
