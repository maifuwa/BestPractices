package com.bigboss.usejdbcjobstore.config;

import com.bigboss.usejdbcjobstore.listener.SchedulerJobListener;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.context.annotation.Configuration;

/**
 * @author: maifuwa
 * @date: 2024/10/12 15:52
 * @description:
 */
@Configuration
@RequiredArgsConstructor
public class SchedulerConfiguration {

    private final SchedulerJobListener schedulerJobListener;

    private final Scheduler scheduler;

    @PostConstruct
    public void addListener() throws SchedulerException {
        scheduler.getListenerManager().addJobListener(schedulerJobListener);
    }
}
