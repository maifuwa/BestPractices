package org.bigboss.springsecuritydemo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * @author: maifuwa
 * @date: 2024/3/17 下午7:37
 * @description: 需要权限的控制器
 */
@RestController
@RequestMapping("/security")
public class SecurityController {

    @RequestMapping("/")
    public String doSecurity() {
        return "Security";
    }

    @RequestMapping("/hello")
    public String doHello() {
        return "Hello";
    }

    @RequestMapping("/admin")
    public String doAdmin() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        String username = authentication.getName();
        Object principal = authentication.getPrincipal();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        return "Admin";
    }
}
