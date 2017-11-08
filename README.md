# 1.RPC-intruder
RPC-intruder 是一个用于开发过程中的RPC辅助测试框架。

其目的是为了解决注册中心不可达，网络环境隔离等情况下，本地应用调试远程服务的问题，以加快开发效率。

严格来说，这么做是不符合安全标准的，但跟app的热修复类似，这是一个为了提升开发效率的折中手段。

**本框架不应该暴露到生成环境中。**

# 2.起步

maven

        <dependency>
            <groupId>com.wchukai</groupId>
            <artifactId>rpc-intruder</artifactId>
            <version>1.0.0</version>
        </dependency>

查看与Dubbo共存的[示例](/examples)

## 2.1 Service端

假设service起在本地，http端口为8080

- 1.配置Bean

    <bean class="com.wchukai.rpcintruder.service.context.InvocationContext"></bean>

- 2.配置Servlet

web.xml
  
    <servlet-name>rpc-intruder</servlet-name>
        <servlet-class>com.wchukai.rpcintruder.service.ServiceServlet</servlet-class>
    </servlet>
    <servlet-mapping>
        <servlet-name>rpc-intruder</servlet-name>
        <url-pattern>/rpc-intruder</url-pattern>
    </servlet-mapping>
      
或Springboot
        
        @Bean
        public ServletRegistrationBean indexServletRegistration() {
            ServletRegistrationBean registration = new ServletRegistrationBean(new ServiceServlet());
            registration.addUrlMappings("/rpc-intruder");
            return registration;
        }
- 3.JVM新增启动参数 `-Drpc.intruder.enabled=true`.
  
  或在classpath下新建文件rpc.intruder.properties,同样配置`rpc.intruder.enabled=true`
    
- 4.打开 `http://127.0.0.1:8080/rpc-intruder` :        

        RPC Intruder enabled!

## 2.2 client端

    <bean class="com.wchukai.rpcintruder.client.ClientConfiguration">
        <property name="classNames">
            <list>
                <!--需要代理的接口-->
                <value>com.wchukai.service.HelloService2</value>
            </list>
        </property>
        <!--http服务路径-->
        <property name="url" value="http://127.0.0.1:8080/rpc-intruder"/>
    </bean>
默认Autowired,这样Dubbo等rpc接口就被Intruder替换掉了。
    
    @Autowired
    private HelloService2 helloService2;

# 3.配置rpc.intruder.properties
    
    # 预加载接口
    rpc.intruder.preload.interface=com.wchukai.service0.HelloWorldService0
    # 自动扫描并预加载接口，多个用分号隔开，主要用于后面的在线测试
    rpc.intruder.preload.package=com.wchukai.service
    #预加载时排除接口或类，分号隔开的正则
    rpc.intruder.preload.excluded=.+Impl;.+impl
    # 实现AbstractInvokeFilter接口的过滤器，可做一些serivce端的自定义拦截操作
    rpc.intruder.invoke.filter=com.wchukai.filter.InvokeFilterImpl
    #激活框架，推荐使用JVM参数方式来激活，以防止意外暴露
    rpc.intruder.enabled=true

# 4.基于页面Json测试

框架内置了一个可用于在线测试接口的功能。

![](https://static.wchukai.com/group1/M00/00/01/cHx_F1oCw3aAc6XeAABoQandamk553.png)

通过输入`http://127.0.0.1/rpc-intruder?action=index` 可进入接口测试页面

# 5.扩展阅读

RPC-intruder本质上是一个RPC框架，基于传输协议为HTTP，序列化框架为Hessian。
但没有生产级服务化框架的注册，负载，服务治理等等功能，因此非常适合集成到开发环境中，即插即用，便于快速测试。

## 5.1传输协议-HTTP

对于多环境的分布式应用，注册中心是隔离的。用来发布rpc服务的端口不会对外开放，也无法直连。
而HTTP的80端口则是一个例外，因此直接使用HTTP，通过80端口执行远程调用是一个可行的方法。

## 5.2序列化-hessian

在RPC框架中，使用何种序列化框架有非常多的讨论，根据不同场景各有考量。
在开发此工具时测试过JDK、json、kryo、hessian等多个框架，但最终只有hessian经受了公司各种复杂的对象和接口的考验（比如一个包含数百字段，几十个对象继承组合而来的大对象）。

我所经历的几个自研的生产级RPC框架中，hessian也是主要的序列化框架，不得不说大家都用的东西都是经过考验的。稳定性，兼容性都是一流水准。

虽然有个小坑就是包括4.07在内的多个版本由于缺失配置导致BigDecimal无法序列化。
本框架已经集成了修复补丁，即src/main/resources/META-INF/hessian下的两个描述文件。

# 6.LICENSE
[The MIT License](LICENSE)