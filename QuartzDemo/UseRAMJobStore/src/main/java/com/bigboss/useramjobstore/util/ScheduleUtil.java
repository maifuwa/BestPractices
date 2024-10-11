package com.bigboss.useramjobstore.util;

import com.bigboss.useramjobstore.domain.JobDetails;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
                .usingJobData("jobData", jobData)
                .storeDurably()
                .build();

        CronTrigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(jobName, jobGroup)
                .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
                .build();

        scheduler.scheduleJob(jobDetail, trigger);
    }

    public static void addJob(JobDetails jobDetails) throws SchedulerException {
        addJob(jobDetails.getJobClassName(), jobDetails.getJobName(), jobDetails.getJobGroup(), jobDetails.getCronExpression(), jobDetails.getJobData());
    }

    public static void deleteJob(String jobName, String jobGroup) throws SchedulerException {
        scheduler.deleteJob(new JobKey(jobName, jobGroup));
    }

}
