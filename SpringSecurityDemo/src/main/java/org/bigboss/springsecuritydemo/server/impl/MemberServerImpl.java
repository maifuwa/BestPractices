package org.bigboss.springsecuritydemo.server.impl;

import lombok.extern.slf4j.Slf4j;
import org.bigboss.springsecuritydemo.domain.Member;
import org.bigboss.springsecuritydemo.domain.MemberDetails;
import org.bigboss.springsecuritydemo.repository.MemberRepository;
import org.bigboss.springsecuritydemo.server.MemberServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
    @Lazy
    private PasswordEncoder passwordEncoder;

    @Override
    public Member register(String username, String password) {
        Member member = Member.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .build();
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("username", ExampleMatcher.GenericPropertyMatchers.exact())
                .withIgnorePaths("id");
        memberRepository.findOne(Example.of(member, matcher))
                .ifPresent(m -> {throw new RuntimeException("用户已存在");});
        return memberRepository.save(member);
    }

    @Override
    public Member info(String username) {
        return memberRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("用户不存在"));
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        Member member = memberRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("用户不存在"));
        return new MemberDetails(member);
    }

    @Override
    public String getToken(String username, String password) {
        return null;
    }

    @Override
    public String refreshToken(String token) {
        return null;
    }
}
