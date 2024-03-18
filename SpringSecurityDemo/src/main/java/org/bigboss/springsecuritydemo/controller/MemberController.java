package org.bigboss.springsecuritydemo.controller;

import org.bigboss.springsecuritydemo.model.CommonResult;
import org.bigboss.springsecuritydemo.server.MemberServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * @author: maifuwa
 * @date: 2024/3/17 下午7:37
 * @description: 用户登录注册获取用户信息控制器
 */
@RestController
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private MemberServer memberServer;

    @GetMapping("/hello")
    public String doHello() {
        return "Hello";
    }

    @PostMapping("/register")
    public CommonResult<?> doSignup(String username, String password) {
        return CommonResult.success(memberServer.register(username, password));
    }

    @GetMapping("/info")
    public CommonResult<?> getInfo() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return CommonResult.success(memberServer.info(username));
    }

    @GetMapping("/refresh")
    public CommonResult<?> refresh(@RequestHeader("Authorization") String token){
        return CommonResult.success(memberServer.refreshToken(token));
    }
}
