package com.bojiw.spring.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: 胡添发
 * @Create: 2019-08-06 10:15
 */
@RestController
@RequestMapping("/demo")
public class DemoController {

    @GetMapping("")
    public String hello() {
        return "index";
    }
}
