package com.prameswaradev.springsecuritypractice;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class WebController {
    
    @GetMapping(value = "/")
    public String publicPage(){
        return "hello Prameswara!";
    }

//    to consume the data of user while log in
    @GetMapping(value = "/private")
    public String privatePage(Authentication authentication){
        return "Hi welcome to our app king " + getName(authentication);
    }

    private String getName(Authentication authentication) {
        return Optional.of(authentication.getPrincipal())
                .filter(OidcUser.class::isInstance)
                .map(OidcUser.class::cast)
                .map(OidcUser::getFullName)
                .orElseGet(authentication::getName);
    }


}
