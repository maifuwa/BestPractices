package com.bigboss.useramjobstore.repository;

import com.bigboss.useramjobstore.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author: maifuwa
 * @date: 2024/10/12 15:05
 * @description:
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
}
