package org.bigboss.springsecuritydemo.config;

import jakarta.servlet.http.HttpServletResponse;
import org.bigboss.springsecuritydemo.filter.JwtFilter;
import org.bigboss.springsecuritydemo.model.CommonResult;
import org.bigboss.springsecuritydemo.server.MemberServer;
import org.bigboss.springsecuritydemo.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

import java.io.IOException;
import java.io.PrintWriter;
/**
 * @author: maifuwa
 * @date: 2024/3/17 下午7:46
 * @description: SpringSecurity配置类
 */
@Configuration
public class SecurityConfig {

    @Autowired
    private MemberServer MemberServer;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return MemberServer::loadUserByUsername;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((config) -> config
                        .requestMatchers("/nosecurity/**").permitAll()
                        .requestMatchers("/member/register").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        // 客制登录页面url
                        .loginProcessingUrl("/api/login").permitAll()
                        .successHandler((request, response, authentication) -> {
                            // 登录成功处理,申请token返回
                        })
                )
                .logout(logout -> logout
                        // 客制退出登录url
                        .logoutUrl("/api/logout")
                        // 客制退出登录处理
                        .logoutSuccessHandler((request, response, authentication) -> {
                            // 退出登录处理，清除token
                        })
                )
                 // 客制异常处理
                .exceptionHandling((exceptions) -> exceptions
                        .authenticationEntryPoint((request, response, authException) -> {
                            // 向浏览器请求登录凭证
                            this.exceptionHandling(response, authException);
                        })
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            // 提示用户权限不足
                            this.exceptionHandling(response, accessDeniedException);
                        })
                )
                .httpBasic(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(new JwtFilter(), AuthorizationFilter.class);
        return http.build();
    }

    private void exceptionHandling(HttpServletResponse response, Exception exception) throws IOException {
        response.setContentType("application/json; charset=UTF-8");
        PrintWriter writer = response.getWriter();
        if (exception instanceof AccessDeniedException) {
            writer.write(JsonUtil.toJson(CommonResult.accessDenied()));
        }else {
            writer.write(JsonUtil.toJson(CommonResult.unauthorized()));
        }
        writer.flush();
    }
}