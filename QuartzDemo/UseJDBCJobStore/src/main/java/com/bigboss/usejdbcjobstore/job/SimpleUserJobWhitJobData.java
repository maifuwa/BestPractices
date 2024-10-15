package com.bigboss.usejdbcjobstore.job;

import com.bigboss.usejdbcjobstore.domain.User;
import com.bigboss.usejdbcjobstore.repository.UserRepository;
import com.bigboss.usejdbcjobstore.util.JsonUtil;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

/**
 * @author: maifuwa
 * @date: 2024/10/9 11:28
 * @description: 简单任务_持久化用户_带JobData
 */
@Slf4j
@Setter
@Component
@RequiredArgsConstructor
public class SimpleUserJobWhitJobData extends QuartzJobBean {

    private final UserRepository userRepository;

    private String userStr;

    @SneakyThrows
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        User user = JsonUtil.toObject(userStr, User.class);
        userRepository.save(user);
        log.info("user id:{}, JobId:{}", user.getId(), this.hashCode());
    }
}
