package org.bigboss.springsecuritydemo.server;

import jakarta.transaction.Transactional;
import org.bigboss.springsecuritydemo.domain.Member;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author: maifuwa
 * @date: 2024/3/17 下午7:44
 * @description: 用户服务
 */
public interface MemberServer {

    @Transactional
    Member register(String username, String password);

    Member info(String username);

    UserDetails loadUserByUsername(String username);

    String buildToken(String username);

    void blackToken(String token);

    String refreshToken(String token);
}
