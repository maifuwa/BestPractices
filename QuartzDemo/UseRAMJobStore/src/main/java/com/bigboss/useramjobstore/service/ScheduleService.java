package com.bigboss.useramjobstore.service;

import com.bigboss.useramjobstore.common.ConcisePage;
import com.bigboss.useramjobstore.dto.JobDetailsParam;
import com.bigboss.useramjobstore.dto.JobDetailsResult;
import com.bigboss.useramjobstore.dto.JobDetailsUpdateParam;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.quartz.SchedulerException;

/**
 * @author: maifuwa
 * @date: 2024/10/11 10:53
 * @description:
 */
public interface ScheduleService {

    void addJob(JobDetailsParam jobDetailsParam) throws SchedulerException;

    void deleteJob(String jobName, String jobGroup) throws SchedulerException;

    String pauseJob(String jobName, String jobGroup, Boolean status) throws SchedulerException;

    void updateJob(JobDetailsUpdateParam jobDetailsUpdateParam) throws SchedulerException;

    ConcisePage<JobDetailsResult> list(Integer page, Integer size);
}
