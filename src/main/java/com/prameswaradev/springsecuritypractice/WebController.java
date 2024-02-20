package com.prameswaradev.springsecuritypractice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebController {
    
    @GetMapping(value = "/")
    public String publicPage(){
        return "hello Prameswara!";
    }
    @GetMapping(value = "/private")
    public String privatePage(){
        return "hello you need permission first!";
    }





    
}
