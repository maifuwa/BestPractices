package com.bigboss.useramjobstore.job;

import com.bigboss.useramjobstore.common.InternalException;
import com.bigboss.useramjobstore.domain.User;
import com.bigboss.useramjobstore.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author: maifuwa
 * @date: 2024/10/14 14:28
 * @description:
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SimpleUserJob {

    private final UserRepository userRepository;

    @Transactional(rollbackOn = InternalException.class)
    public void execute(String name, Integer age) {
        User user = User.builder().name(name).age(age).build();
        userRepository.save(user);
        log.info("SimpleUserJob insert user id:{} name:{}, age:{}", user.getId(), name, age);
        log.info("SimpleUserJob: {}", this.hashCode());
        throw new InternalException("测试事务回滚");
    }
}
