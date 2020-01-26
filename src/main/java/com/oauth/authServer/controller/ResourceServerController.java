package com.oauth.authServer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResourceServerController {

    @GetMapping("/getResource")
    public String getResource(){
        return "success";
    }
}
