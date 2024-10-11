package com.bigboss.usejdbcjobstore.service;

import com.bigboss.usejdbcjobstore.pojo.JobInfoVo;
import org.quartz.SchedulerException;

import java.util.List;
import java.util.Map;

/**
 * @author: maifuwa
 * @date: 2024/10/7 10:52
 * @description: 任务调度服务
 */
public interface ScheduleService {

    String addJob(String jobBean, String jobName, String jobGroup, String cronExpression, Map<String, String> jobData) throws SchedulerException;

    String deleteJob(String jobName, String jobGroup) throws SchedulerException;

    String pauseJob(String jobName, String jobGroup, Boolean status) throws SchedulerException;

    void updateJobKey(String jobName, String jobGroup, String newJobName, String newJobGroup) throws SchedulerException;

    void updateJobCron(String jobName, String jobGroup, String cronExpression) throws SchedulerException;

    void updateJobData(String newJobName, String newJobGroup, Map<String, String> newJobData) throws SchedulerException;

    List<JobInfoVo> list() throws SchedulerException;

}
