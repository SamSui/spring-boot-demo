package com.xisui.springboottest.proxy.proxyFactory;

import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.Pointcut;
import org.springframework.aop.PointcutAdvisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.aop.target.SingletonTargetSource;

import java.lang.reflect.Method;

public class TestAop {
    public static void main(String[] args) {
        ProxyFactory factory = new ProxyFactory(new PersonService()) ;
        //factory.setProxyTargetClass(true) ;

        factory.setProxyTargetClass(false) ;
        // 设置通知类（内部会自动的包装为Advisor）
        factory.addAdvice(new MethodInterceptor() {
            @Override
            public Object invoke(MethodInvocation invocation) throws Throwable {
                System.out.println("权限控制...") ;
                return invocation.proceed() ;
            }
        });
        factory.addAdvisor(new PointcutAdvisor() {
            @Override
            public Advice getAdvice() {
                return new MethodInterceptor() {
                    @Override
                    public Object invoke(MethodInvocation invocation) throws Throwable {
                        System.out.println("日志记录...") ;
                        return invocation.proceed() ;
                    }
                } ;
            }
            @Override
            public Pointcut getPointcut() {
                return new StaticMethodMatcherPointcut() {
                    @Override
                    public boolean matches(Method method, Class<?> targetClass) {
                        return method.getName().equals("save") ;
                    }
                } ;
            }
        }) ;
        CommonDAO ps = (CommonDAO) factory.getProxy() ;
        ps.save() ;
    }
}
