package com.bigboss.usedjdbcjobstore.Service;

import org.quartz.SchedulerException;

/**
 * @description:
 * @author: maifuwa
 * @date: 2024/10/7 10:52
 */
public interface ScheduleService {

    String addJob(String jobBean, String jobName, String jobGroup, String cronExpression) throws SchedulerException, ClassNotFoundException;
}
