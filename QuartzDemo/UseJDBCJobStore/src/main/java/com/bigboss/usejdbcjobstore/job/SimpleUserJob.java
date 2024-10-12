package com.bigboss.usejdbcjobstore.job;

import com.bigboss.usejdbcjobstore.domain.User;
import com.bigboss.usejdbcjobstore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

/**
 * @author: maifuwa
 * @date: 2024/10/8 10:53
 * @description: 简单任务_持久化用户
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SimpleUserJob implements Job {

    private final UserRepository userRepository;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        User user = new User();
        user.setName("bigboss");
        user.setAge(18);
        userRepository.save(user);
        log.info("user id:{}, JobId:{}", user.getId(), this.hashCode());
        throw new RuntimeException("测试异常");
    }
}
