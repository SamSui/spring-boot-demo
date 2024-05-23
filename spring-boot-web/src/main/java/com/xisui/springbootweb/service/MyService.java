package com.xisui.springbootweb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MyService {

    @Autowired
    private SingletonBean singletonBean;

    public void performAction() {
        singletonBean.usePrototypeBean();
    }

    public void performAction2() {
        singletonBean.usePrototypeBean2();
    }
}
