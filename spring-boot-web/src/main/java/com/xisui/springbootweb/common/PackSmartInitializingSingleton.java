package com.xisui.springbootweb.common;

import com.xisui.springbootweb.service.PersonService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class PackSmartInitializingSingleton implements SmartInitializingSingleton {

    @Resource
    private ApplicationContext context ;

    @Override
    public void afterSingletonsInstantiated() {
        // 在这里你可以执行一些其它逻辑或者对当前容器中的bean进行相应的操作
        // TODO
        PersonService us = context.getBean(PersonService.class) ;
        us.savePerson("sdfdf") ;
    }
}
