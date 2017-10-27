package com.wchukai.rpcintruder.service;

import com.wchukai.rpcintruder.service.context.InvocationContext;
import com.wchukai.rpcintruder.service.context.InvokeFilter;
import com.wchukai.rpcintruder.util.PackageScanner;
import com.wchukai.rpcintruder.util.PropertyUtil;
import com.wchukai.rpcintruder.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @author chukai
 */
public class ServiceConfiguration {
    private static final Logger LOGGER = LoggerFactory.getLogger(InvocationContext.class);
    private List<String> classNames = new ArrayList<>();
    private boolean enabled;
    private InvokeFilter invokeFilter;

    public List<String> getClassNames() {
        return classNames;
    }

    public void setClassNames(List<String> classNames) {
        this.classNames = classNames;
    }

    public boolean isEnable() {
        return enabled;
    }

    public void initProperties() throws Exception {
        Properties properties = PropertyUtil.loadFile("/rpc.intruder.properties");
        String enable = System.getenv("rpc.intruder.enabled");
        if (StringUtil.isEmpty(enable)) {
            enable = System.getProperty("rpc.intruder.enabled");
            if (StringUtil.isEmpty(enable)) {
                enable = properties.getProperty("rpc.intruder.enabled");
            }
        }
        this.enabled = "true".equalsIgnoreCase(enable);
        if (!enabled) {
            return;
        }
        String services = properties.getProperty("rpc.intruder.preload.interface");
        if (StringUtil.isNotEmpty(null)) {
            String[] classes = services.split(";");
            classNames.addAll(Arrays.asList(classes));
        }
        String pack = properties.getProperty("rpc.intruder.preload.package");
        if (StringUtil.isNotEmpty(pack)) {
            String[] packages = pack.split(";");
            PackageScanner packageScanner = new PackageScanner();
            for (int i = 0; i < packages.length; i++) {
                classNames.addAll(packageScanner.scan(packages[i]));
            }
        }
        String exclued = properties.getProperty("rpc.intruder.preload.excluded");
        if (StringUtil.isNotEmpty(exclued)) {
            Iterator<String> iterator = classNames.iterator();
            String[] regexs = exclued.split(";");
            iter:
            while (iterator.hasNext()) {
                String next = iterator.next();
                for (int i = 0; i < regexs.length; i++) {
                    if (next.matches(regexs[i])) {
                        iterator.remove();
                        continue iter;
                    }
                }
            }
        }
        String filterClass = properties.getProperty("rpc.intruder.invoke.filter");
        if (StringUtil.isNotEmpty(filterClass)) {
            this.invokeFilter = (InvokeFilter) Class.forName(filterClass).newInstance();
        }
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("{}", this.toString());
        }
    }

    public InvokeFilter getInvokeFilter() {
        return invokeFilter;
    }

    public void setInvokeFilter(InvokeFilter invokeFilter) {
        this.invokeFilter = invokeFilter;
    }

    @Override
    public String toString() {
        return "ServiceConfiguration{" +
                "classNames=" + classNames +
                ", invokeFilter=" + invokeFilter +
                '}';
    }
}
