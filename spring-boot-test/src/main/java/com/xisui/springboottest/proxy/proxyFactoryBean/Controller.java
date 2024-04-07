package com.xisui.springboottest.proxy.proxyFactoryBean;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/proxy")
public class Controller {
    @Resource
    private CommonDAO personService;
    //private PersonService personService;
    @RequestMapping("/test")
    public String test() {
        personService.save();
        return "proxy test";
    }
}
