package com.bigboss.useramjobstore.util;

import com.bigboss.useramjobstore.domain.JobDetails;
import com.bigboss.useramjobstore.job.base.QuartzDisallowConcurrentExecution;
import com.bigboss.useramjobstore.job.base.QuartzJobExecution;
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

    private static void addJob(String jobName, String jobGroup, String invokeTarget, String cronExpression, Boolean concurrent) throws SchedulerException {
        Class<? extends Job> jobClass = concurrent ? QuartzJobExecution.class : QuartzDisallowConcurrentExecution.class;

        JobDetail jobDetail = JobBuilder.newJob(jobClass)
                .withIdentity(jobName, jobGroup)
                .usingJobData("invokeTarget", invokeTarget)
                .storeDurably()
                .build();

        CronTrigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(jobName, jobGroup)
                .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
                .build();

        scheduler.scheduleJob(jobDetail, trigger);
    }

    public static void addJob(JobDetails jobDetails) throws SchedulerException {
        addJob(jobDetails.getJobName(), jobDetails.getJobGroup(), jobDetails.getInvokeTarget(), jobDetails.getCronExpression(), jobDetails.getConcurrent());
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
