package com.bigboss.usedjdbcjobstore.service.impl;

import com.bigboss.usedjdbcjobstore.common.GlobalException;
import com.bigboss.usedjdbcjobstore.pojo.JobInfoVo;
import com.bigboss.usedjdbcjobstore.service.ScheduleService;
import com.bigboss.usedjdbcjobstore.util.SpringUtil;
import lombok.RequiredArgsConstructor;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final Scheduler scheduler;

    @SuppressWarnings("unchecked")
    @Override
    public String addJob(String jobBean, String jobName, String jobGroup, String cronExpression, Map<String, String> jobData) throws SchedulerException {
        JobDetail jobDetail = JobBuilder.newJob((Class<? extends Job>) SpringUtil.getBeanClass(jobBean))
                .usingJobData(new JobDataMap(jobData))
                .withIdentity(jobName, jobGroup)
                .storeDurably()
                .build();

        CronTrigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(jobName, jobGroup)
                .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
                .build();

        scheduler.scheduleJob(jobDetail, trigger);
        return "Job added successfully";
    }

    @Override
    public String deleteJob(String jobName, String jobGroup) throws SchedulerException {
        scheduler.deleteJob(new JobKey(jobName, jobGroup));
        return "Job deleted successfully";
    }

    @Override
    public String pauseJob(String jobName, String jobGroup, Boolean status) throws SchedulerException {
        JobKey jobKey = new JobKey(jobName, jobGroup);
        if (status) {
            scheduler.pauseJob(jobKey);
            return "Job paused successfully";
        } else {
            scheduler.resumeJob(jobKey);
            return "Job resumed successfully";
        }
    }

    @Override
    public void updateJobKey(String jobName, String jobGroup, String newJobName, String newJobGroup) throws SchedulerException {
        JobKey oldJobKey = new JobKey(jobName, jobGroup);
        JobKey newJobKey = new JobKey(newJobName, newJobGroup);

        JobDetail jobDetail = scheduler.getJobDetail(oldJobKey);
        List<? extends Trigger> triggers = scheduler.getTriggersOfJob(oldJobKey);

        scheduler.deleteJob(oldJobKey);

        JobBuilder jb = jobDetail.getJobBuilder();
        JobDetail newJobDetail = jb.withIdentity(newJobKey).build();

        Set<Trigger> newTriggers = triggers.stream()
                .map(oldTrigger -> oldTrigger.getTriggerBuilder().withIdentity(newJobName, newJobGroup).build())
                .collect(Collectors.toSet());

        scheduler.scheduleJob(newJobDetail, newTriggers, true);
    }

    @Override
    public void updateJobCron(String jobName, String jobGroup, String cronExpression) throws SchedulerException {
        TriggerKey triggerKey = new TriggerKey(jobName, jobGroup);
        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

        CronTrigger newTrigger = trigger.getTriggerBuilder()
                .withIdentity(triggerKey)
                .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
                .build();

        scheduler.rescheduleJob(triggerKey, newTrigger);
    }

    @Override
    public void updateJobData(String jobName, String jobGroup, Map<String, String> newJobData) throws SchedulerException {
        JobDetail jobDetail = scheduler.getJobDetail(new JobKey(jobName, jobGroup));
        JobBuilder jobBuilder = jobDetail.getJobBuilder();

        scheduler.addJob(jobBuilder.setJobData(new JobDataMap(newJobData)).build(), true);
    }

    @Override
    public List<JobInfoVo> list() throws SchedulerException {
        List<JobDetail> jobList = new ArrayList<>();
        for (String groupName : scheduler.getJobGroupNames()) {
            for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {
                JobDetail jobDetail = scheduler.getJobDetail(jobKey);
                jobList.add(jobDetail);
            }
        }

        return jobList.stream().map(jobDetail -> {
            try {
                return JobInfoVo.builder()
                        .jobBean(jobDetail.getJobClass().getSimpleName())
                        .jobName(jobDetail.getKey().getName())
                        .jobGroup(jobDetail.getKey().getGroup())
                        .cronExpression(getCronExpression(jobDetail.getKey()))
                        .jobData(jobDetail.getJobDataMap().getWrappedMap()
                                .entrySet()
                                .stream()
                                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().toString()))
                        )
                        .build();
            } catch (SchedulerException e) {
                throw new GlobalException("Failed to list jobs", e);
            }
        }).toList();
    }

    private String getCronExpression(JobKey jobKey) throws SchedulerException {
        TriggerKey triggerKey = new TriggerKey(jobKey.getName(), jobKey.getGroup());
        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        return trigger.getCronExpression();
    }
}