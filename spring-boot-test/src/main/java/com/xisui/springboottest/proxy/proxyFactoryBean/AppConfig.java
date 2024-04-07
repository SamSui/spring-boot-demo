package com.xisui.springboottest.proxy.proxyFactoryBean;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.target.SingletonTargetSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
  @Bean
  public MethodInterceptor logInterceptor() {
    return new MethodInterceptor() {
      @Override
      public Object invoke(MethodInvocation invocation) throws Throwable {
        System.out.println("日志记录...") ;
        return invocation.proceed() ;
      }
    };
  }
  @Bean
  public ProxyFactoryBean personService() throws Exception {
    ProxyFactoryBean proxy = new ProxyFactoryBean() ;
    proxy.setProxyTargetClass(false) ;
    proxy.setTargetSource(new SingletonTargetSource(new PersonService())) ;
    proxy.setProxyInterfaces(new Class<?>[] {CommonDAO.class}) ;
    proxy.setInterceptorNames("logInterceptor") ;
    return proxy ;
  }
}
