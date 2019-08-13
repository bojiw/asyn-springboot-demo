package com.bojiw.spring.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.bojiw.spring.demo"})
public class AsyncmdbootdemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(AsyncmdbootdemoApplication.class, args);
    }

}
