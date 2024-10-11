package com.bigboss.useramjobstore.job;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

/**
 * @author: maifuwa
 * @date: 2024/10/11 14:38
 * @description:
 */
@Slf4j
@Setter
@Component
public class SimpleJob extends QuartzJobBean {

    private String jobData;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        log.info("simple job execute, jobData: {}", jobData);
    }
}
