package com.wchukai.filter;

import com.wchukai.rpcintruder.service.context.AbstractInvokeFilter;
import com.wchukai.rpcintruder.service.context.InvokerBody;

/**
 * @author chukai
 */
public class InvokeFilterImpl extends AbstractInvokeFilter {
    @Override
    public Object invoke(InvokerBody invokerBody) throws Exception {
        return invokerBody.invoke();
    }
}
