package com.prameswaradev.springsecuritypractice;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
/*
        Authentication it means who is the user
        Authorization is the user allowed to perform bla bla bla

        Authentication object represents the user
            Principal: user information like full name, email, and given name
            GrantedAuthorities: it's a role of a user "permissions" like a roles,etc
            details: more information about http request, ip, and more
            Credentials: "password", often null because many cases when we logged in using Google,
                         we shouldn't the pass of user

            method isAuthenticated always true! means if visit the url which secured it will return false


        https://docs.spring.io/spring-security/reference/servlet/authentication/architecture.html#servlet-authentication-granted-authority
 */

    @Order(100)
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeHttpRequests(
                        authorizeConfig -> {
                            authorizeConfig.requestMatchers("/").permitAll();
                            authorizeConfig.requestMatchers("/error").permitAll();
                            authorizeConfig.requestMatchers("/favicon.ico").permitAll();
                            authorizeConfig.requestMatchers("/private").authenticated();
//                            authorizeConfig.anyRequest().permitAll();
                        })
                .formLogin(withDefaults())
                .oauth2Login(withDefaults())
                .build();
    }

    @Order(200)
    @Bean
    public UserDetailsService userDetailsService(){
        return new InMemoryUserDetailsManager(
                User.builder()
                        .username("dikah")
                        .password("{noop}password")
                        .authorities("ROLE_user")
                        .build()
        );
    }
}
