package org.bigboss.springsecuritydemo.server.impl;

import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.bigboss.springsecuritydemo.controller.exception.ApiException;
import org.bigboss.springsecuritydemo.domain.Member;
import org.bigboss.springsecuritydemo.domain.MemberDetails;
import org.bigboss.springsecuritydemo.domain.Role;
import org.bigboss.springsecuritydemo.repository.MemberRepository;
import org.bigboss.springsecuritydemo.repository.RoleRepository;
import org.bigboss.springsecuritydemo.server.MemberServer;
import org.bigboss.springsecuritydemo.server.RedisServer;
import org.bigboss.springsecuritydemo.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author: maifuwa
 * @date: 2024/3/17 下午7:44
 * @description: 用户服务实现类
 */
@Slf4j
@Service
public class MemberServerImpl implements MemberServer {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RedisServer redisServer;

    @Override
    public Member register(String username, String password) {
        memberRepository.findByUsername(username).ifPresent(m -> {
            throw new ApiException("用户已存在");
        });
        Set<Role> roles = Set.of(roleRepository.findAllByName("USER"));
        Member member = Member.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .roles(roles)
                .build();
        return memberRepository.save(member);
    }

    @Override
    public Member info(String username) {
        return memberRepository.findByUsername(username).orElseThrow(() -> new ApiException("用户不存在"));
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        Member member = memberRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("用户不存在"));
        return new MemberDetails(member);
    }

    @Override
    public String buildToken(String username) {
        Member member = memberRepository.findByUsername(username).orElseThrow(() -> new ApiException("用户不存在"));
        return JwtUtil.generateToken(member.getId(), member.getUsername());
    }

    @Override
    public void blackToken(String token) {
        redisServer.setWithExp(token, "black", JwtUtil.getRemainingExp(token), TimeUnit.MILLISECONDS);
    }

    @Override
    public String refreshToken(String token) {
        Integer id;
        try {
            id = JwtUtil.getId(token);
        } catch (JwtException e) {
            throw new ApiException(e.getMessage());
        }
        blackToken(token);
        Member member = memberRepository.findById(id).orElseThrow(() -> new ApiException("用户不存在"));
        return JwtUtil.generateToken(member.getId(), member.getUsername());
    }
}
