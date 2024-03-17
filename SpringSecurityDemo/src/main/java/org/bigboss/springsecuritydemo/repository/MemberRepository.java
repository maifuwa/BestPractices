package org.bigboss.springsecuritydemo.repository;

import org.bigboss.springsecuritydemo.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author: maifuwa
 * @date: 2024/3/17 下午7:41
 */
public interface MemberRepository extends JpaRepository<Member, Integer> {

    Optional<Member> findByUsername(String username);
}
