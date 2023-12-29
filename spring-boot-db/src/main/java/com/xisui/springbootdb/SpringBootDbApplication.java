package com.xisui.springbootdb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.xisui.springbootdb")
public class SpringBootDbApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDbApplication.class, args);
    }

}
