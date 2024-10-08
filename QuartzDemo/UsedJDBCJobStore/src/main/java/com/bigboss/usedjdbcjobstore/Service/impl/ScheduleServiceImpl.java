package com.bigboss.usedjdbcjobstore.Service.impl;

import com.bigboss.usedjdbcjobstore.Service.ScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

/**
 * @description:
 * @author: maifuwa
 * @date: 2024/10/7 10:52
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private static final String JOB_BEAN_PATH = "com.bigboss.usedjdbcjobstore.job.";

    private final Scheduler scheduler;

    @Override
    public String addJob(String jobBean, String jobName, String jobGroup, String cronExpression) throws ClassNotFoundException, SchedulerException {
        log.info("add job: {}-{}-{}-{}", jobBean, jobName, jobGroup, cronExpression);

        scheduler.scheduleJob(buildJobDetail(jobName, jobGroup, getJobClass(jobBean), null),
                buildTrigger(jobName, jobGroup, cronExpression));
        return "添加成功";
    }

    private Class<? extends Job> getJobClass(String jobBeanName) throws ClassNotFoundException {
        return Class.forName(JOB_BEAN_PATH + jobBeanName).asSubclass(Job.class);
    }

    private JobDetail buildJobDetail(String jobName, String jobGroup, Class<? extends Job> jobClass, Map<String, Object> jobData) {
        return JobBuilder.newJob(jobClass)
                .withIdentity(jobName, jobGroup)
                .storeDurably()
                .usingJobData(new JobDataMap(Optional.ofNullable(jobData).orElseGet(Collections::emptyMap)))
                .build();
    }

    private Trigger buildTrigger(String jobName, String jobGroup, String cronExpression) {
        return TriggerBuilder.newTrigger()
                .withIdentity(jobName, jobGroup)
                .startNow()
                .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
                .build();
    }
}
