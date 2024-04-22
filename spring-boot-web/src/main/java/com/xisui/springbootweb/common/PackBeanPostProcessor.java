//package com.xisui.springbootweb.common;
//
//import org.springframework.beans.BeansException;
//import org.springframework.beans.factory.config.BeanPostProcessor;
//import org.springframework.stereotype.Component;
//
//@Component
//public class PackBeanPostProcessor implements BeanPostProcessor {
//
//    @Override
//    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
//        System.out.println("初始化之前处理逻辑...") ;
//        // TODO
//        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName) ;
//    }
//    @Override
//    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
//        System.out.println("初始化之后处理逻辑...") ;
//        // TODO
//        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
//    }
//}
