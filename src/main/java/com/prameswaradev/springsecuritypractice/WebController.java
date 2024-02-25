package com.prameswaradev.springsecuritypractice;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/*
        Authentication it means who is the user
        Authorization is the user allowed to perform bla bla bla

        Authentication object represents the user
            Principal: user information like full name, email, and given name
            GrantedAuthorities: it's a role of a user "permissions" like a roles,etc

        https://docs.spring.io/spring-security/reference/servlet/authentication/architecture.html#servlet-authentication-granted-authority
 */
@RestController
public class WebController {

    @GetMapping(value = "/")
    public String publicPage(){
        var authentication = SecurityContextHolder.getContext().getAuthentication(); // best practice
        return "Hi welcome to our app king " + getName(authentication);
    }

//    to consume the data of user while log in
    @GetMapping(value = "/private")
    public String privatePage() throws InterruptedException {
//        this is thread local var means only available while processing the request
//        if spawned more thread they not have access to securityContext
        var authentication = SecurityContextHolder.getContext().getAuthentication(); // best practice
        System.out.println("OUTSIDE THREAD: " + authentication);
//            just as an example the assertion that Security Context is not automatically inherited into new threads
        Thread thread = new Thread(() -> {
            var inThread = SecurityContextHolder.getContext().getAuthentication();
            System.out.println("IN THREAD: " + inThread);
        });
        thread.start();
        thread.join();
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
