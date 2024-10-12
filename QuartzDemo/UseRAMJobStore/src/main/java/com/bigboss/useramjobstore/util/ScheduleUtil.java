package com.bigboss.useramjobstore.util;

import com.bigboss.useramjobstore.common.GlobalException;
import com.bigboss.useramjobstore.domain.JobDetails;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

/**
 * @author: maifuwa
 * @date: 2024/10/11 11:26
 * @description:
 */
@Component
public class ScheduleUtil {

    private static Scheduler scheduler;

    @Autowired
    public void setScheduler(Scheduler scheduler) {
        ScheduleUtil.scheduler = scheduler;
    }

    @SuppressWarnings("unchecked")
    public static void addJob(String jobClassName, String jobName, String jobGroup, String cronExpression, String jobData) throws SchedulerException {
        JobDetail jobDetail = JobBuilder.newJob((Class<? extends Job>) SpringUtil.getBeanClass(jobClassName))
                .withIdentity(jobName, jobGroup)
                .usingJobData(new JobDataMap(jobDataStrToMap(jobData)))
                .storeDurably()
                .build();

        CronTrigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(jobName, jobGroup)
                .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
                .build();

        scheduler.scheduleJob(jobDetail, trigger);
    }

    private static Map<String, String> jobDataStrToMap(String jobData) {
        return Optional.ofNullable(jobData)
                .map(data -> {
                    try {
                        return JsonUtil.toMap(data, String.class, String.class);
                    } catch (JsonProcessingException e) {
                        throw GlobalException.badRequest("Job data format error", e);
                    }
                })
                .orElse(Collections.emptyMap());
    }

    public static void addJob(JobDetails jobDetails) throws SchedulerException {
        addJob(jobDetails.getJobClassName(), jobDetails.getJobName(), jobDetails.getJobGroup(), jobDetails.getCronExpression(), jobDetails.getJobData());
    }

    public static void deleteJob(String jobName, String jobGroup) throws SchedulerException {
        scheduler.deleteJob(new JobKey(jobName, jobGroup));
    }

    public static void pauseJob(String jobName, String jobGroup) throws SchedulerException {
        scheduler.pauseJob(new JobKey(jobName, jobGroup));
    }

    public static void resumeJob(String jobName, String jobGroup) throws SchedulerException {
        scheduler.resumeJob(new JobKey(jobName, jobGroup));
    }

    public static boolean isJobExist(String jobName, String jobGroup) throws SchedulerException {
        return scheduler.checkExists(new JobKey(jobName, jobGroup));
    }
}
