package com.bigboss.usedjdbcjobstore.repository;

import com.bigboss.usedjdbcjobstore.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author: maifuwa
 * @date: 2024/10/8 20:45
 * @description: 用户持久层
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
}
