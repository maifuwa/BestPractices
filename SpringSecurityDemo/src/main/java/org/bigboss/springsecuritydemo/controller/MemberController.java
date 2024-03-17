package org.bigboss.springsecuritydemo.controller;

import org.bigboss.springsecuritydemo.model.CommonResult;
import org.bigboss.springsecuritydemo.server.MemberServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/register")
    public CommonResult<?> doSignup(String username, String password) {
        return CommonResult.success(memberServer.register(username, password));
    }

    @GetMapping("/info")
    public CommonResult<?> getInfo() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return CommonResult.success(memberServer.info(username));
    }
}
