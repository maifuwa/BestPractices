package com.bigboss.usedjdbcjobstore.job;

import com.bigboss.usedjdbcjobstore.domain.User;
import com.bigboss.usedjdbcjobstore.repository.UserRepository;
import com.bigboss.usedjdbcjobstore.util.JsonUtil;
import lombok.RequiredArgsConstructor;
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
@Component
@RequiredArgsConstructor
public class SimpleUserJobWhitJobData extends QuartzJobBean {

    private final UserRepository userRepository;

    @SneakyThrows
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        User user = JsonUtil.toObject((String) context.getMergedJobDataMap().get("user"), User.class);
        userRepository.save(user);
        log.info("user id:{}, JobId:{}", user.getId(), this.hashCode());
    }
}
