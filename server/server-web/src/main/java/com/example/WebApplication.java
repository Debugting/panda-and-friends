package com.example;

import com.example.utils.JVMCallBack;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.*.*.dao")
public class WebApplication {

    public static void main(String[] args) {
        JVMCallBack.JVMCallBack(5);
        SpringApplication.run(WebApplication.class, args);
    }
}