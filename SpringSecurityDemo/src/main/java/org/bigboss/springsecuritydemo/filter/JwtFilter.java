package org.bigboss.springsecuritydemo.filter;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.bigboss.springsecuritydemo.server.MemberServer;
import org.bigboss.springsecuritydemo.server.RedisServer;
import org.bigboss.springsecuritydemo.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * @author: maifuwa
 * @date: 2024/3/17 下午7:47
 * @description: Jwt过滤器
 */
@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private MemberServer memberServer;

    @Autowired
    private RedisServer redisServer;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = request.getHeader("Authorization");
        if (token != null && !redisServer.hasKey(token)) {
            String username;
            try {
                username = JwtUtil.getSubject(token);
            } catch (JwtException e) {
                filterChain.doFilter(request, response);
                return;
            }
            UserDetails userDetails = memberServer.loadUserByUsername(username);

            SecurityContext context = SecurityContextHolder.createEmptyContext();
            Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, userDetails.getAuthorities());
            context.setAuthentication(authentication);
            SecurityContextHolder.setContext(context);

        }
        filterChain.doFilter(request, response);
    }
}
