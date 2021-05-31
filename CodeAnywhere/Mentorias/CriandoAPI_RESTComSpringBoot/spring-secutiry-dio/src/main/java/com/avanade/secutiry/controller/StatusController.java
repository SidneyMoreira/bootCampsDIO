package com.avanade.secutiry.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatusController {
    
    @GetMapping("/status/ping")
    public String ping(){
        return "Pong";
    }
}
