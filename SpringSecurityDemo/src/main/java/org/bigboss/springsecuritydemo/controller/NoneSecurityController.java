package org.bigboss.springsecuritydemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: maifuwa
 * @date: 2024/3/17 下午7:37
 * @description: 无需权限的控制器
 */
@RestController
@RequestMapping("/nosecurity")
public class NoneSecurityController {

    @GetMapping("/hello")
    public String doHello() {
        return "Hello";
    }

}
