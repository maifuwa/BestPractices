package org.bigboss.springsecuritydemo.repository;

import org.bigboss.springsecuritydemo.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author: maifuwa
 * @date: 2024/3/18 下午8:49
 * @description: 角色仓库
 */
public interface RoleRepository extends JpaRepository<Role, Integer> {

    Role findAllByName(String name);
}
