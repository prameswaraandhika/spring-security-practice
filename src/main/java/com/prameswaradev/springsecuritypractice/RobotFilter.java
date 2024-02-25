package com.prameswaradev.springsecuritypractice;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class RobotFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var password = request.getHeader("x-robot-password");
        if (!"password".equals(password)){
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-type", "text/plain;charset=utf-8");
            response.getWriter().println("You're not my guest");
            return;
        }

        var context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(new RobotAuthentication());
        SecurityContextHolder.setContext(context);
        filterChain.doFilter(request, response);
        return;
    }
}
