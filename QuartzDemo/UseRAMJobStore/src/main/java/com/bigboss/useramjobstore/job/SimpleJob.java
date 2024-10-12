package com.bigboss.useramjobstore.job;

import com.bigboss.useramjobstore.domain.User;
import com.bigboss.useramjobstore.repository.UserRepository;
import com.bigboss.useramjobstore.util.JsonUtil;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

/**
 * @author: maifuwa
 * @date: 2024/10/11 14:38
 * @description:
 */
@Slf4j
@Setter
@Component
@RequiredArgsConstructor
public class SimpleJob extends QuartzJobBean {

    private final UserRepository userRepository;

    private String user;

    private String str;

    @SneakyThrows
    @Override
    protected void executeInternal(JobExecutionContext context) {
        log.info("simple job execute, str: {}", str);
        log.info("user: {}", user);
        if (!ObjectUtils.isEmpty(user)) {
            userRepository.save(JsonUtil.toObject(user, User.class));
        }
    }
}
