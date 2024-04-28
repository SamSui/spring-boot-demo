package com.xisui.springbootdb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.xisui.springbootdb")
@MapperScan(basePackages = "com.xisui.springbootdb.mapper")
public class SpringBootDbApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDbApplication.class, args);
    }

}
