package com.bigboss.usedjdbcjobstore.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @description:
 * @author: maifuwa
 * @date: 2024/10/8 10:53
 */
@Slf4j
public class SimpleJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("SimpleJob执行时间: {}, jobName:{}, jobGroup:{}", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), jobExecutionContext.getJobDetail().getKey().getName(), jobExecutionContext.getJobDetail().getKey().getGroup());
    }
}
