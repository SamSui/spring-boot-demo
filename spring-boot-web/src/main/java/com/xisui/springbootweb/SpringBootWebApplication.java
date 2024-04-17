package com.xisui.springbootweb;

import com.xisui.springbootweb.service.PersonService;
import com.xisui.springbootweb.service.ReplacementSavePerson;
import org.springframework.beans.factory.support.ReplaceOverride;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

@SpringBootApplication(scanBasePackages = "com.xisui.springbootweb")
public class SpringBootWebApplication implements CommandLineRunner {

    private final GenericApplicationContext context;

    public SpringBootWebApplication(GenericApplicationContext context) {
        this.context = context;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBootWebApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        context.registerBean("replacementSavePerson", ReplacementSavePerson.class) ;
        context.registerBean(PersonService.class, bd -> {
            if (bd instanceof RootBeanDefinition root) {
                 //参数savePerson, 指明要替换的方法
                 //replacementSavePerson, 指明要使用容器中哪个Bean来替换
                ReplaceOverride replace = new ReplaceOverride("savePerson", "replacementSavePerson") ;
                replace.addTypeIdentifier("String");
                root.getMethodOverrides().addOverride(replace) ;
            }
        }) ;
    }
}
