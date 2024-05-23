package com.xisui.springbootweb.service;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Component;

@Component
public class SingletonBean {

    /**
     * 虽然PrototypeBean是原型单例，但是在单例实例SingletonBean中通过@resource注入PrototypeBean，
     * 通过单例SingletonBean每次调用prototypeBean.doSomething()方法时，都会返回同一个PrototypeBean实例。
     */
    @Resource
    private PrototypeBean prototypeBean;

    /**
     * 在Spring容器中，PrototypeBean是scope原型单例的，但是它的getPrototypeBean()方法是原型的（因为@Lookup注解），
     * 所以每次调用getPrototypeBean()方法都会返回一个新的PrototypeBean实例。
     */
    @Lookup
    public PrototypeBean getPrototypeBean() {
        // Spring will override this method to return a new PrototypeBean instance
        return null;
    }

    public void usePrototypeBean() {
        getPrototypeBean().doSomething();
    }

    public void usePrototypeBean2() {
        prototypeBean.doSomething();
    }
}
