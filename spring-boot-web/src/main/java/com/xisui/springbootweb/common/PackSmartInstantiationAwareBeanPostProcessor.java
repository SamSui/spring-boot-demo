package com.xisui.springbootweb.common;

import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.SmartInstantiationAwareBeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class PackSmartInstantiationAwareBeanPostProcessor implements SmartInstantiationAwareBeanPostProcessor {

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
// 在实例化之前（也就是当前的对象还没有创建，你可以在这里创建代理对象进行返回）
        return null ;
    }
    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
// 实例化之后，你可以在这里对当前已经创建的bean对象进行相应的操作，只要你这里返回的false，那么后续的自动属性填充将不会执行
        return true ;
    }
    @Override
    public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName) throws BeansException {
// 属性填充，你可以根据自己的需要动态的对当前bean对象进行修改，如属性的设置等
        return null ;
    }
    @Override
    public Object getEarlyBeanReference(Object bean, String beanName) throws BeansException {
// 提前暴露对象
        return bean ;
    }
}
