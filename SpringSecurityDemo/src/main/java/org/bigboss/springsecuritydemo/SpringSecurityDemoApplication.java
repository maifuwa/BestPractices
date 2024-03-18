package org.bigboss.springsecuritydemo;

import jakarta.annotation.PostConstruct;
import org.bigboss.springsecuritydemo.domain.Role;
import org.bigboss.springsecuritydemo.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.List;

@EnableJpaAuditing
@SpringBootApplication
public class SpringSecurityDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityDemoApplication.class, args);
    }

    @Autowired
    private RoleRepository repository;

    @PostConstruct
    public void init() {
        Role user = Role.builder().name("USER").build();
        Role admin = Role.builder().name("ADMIN").build();
        repository.saveAll(List.of(user, admin));
    }

}
