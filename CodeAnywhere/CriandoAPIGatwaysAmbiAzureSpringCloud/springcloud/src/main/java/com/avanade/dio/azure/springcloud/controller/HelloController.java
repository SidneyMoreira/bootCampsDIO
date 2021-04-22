package com.avanade.dio.azure.springcloud.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @RequestMapping("/")
    public String index(){return "Grettings from Azure Spring Cloud Code Anywhere!!!";}
}
