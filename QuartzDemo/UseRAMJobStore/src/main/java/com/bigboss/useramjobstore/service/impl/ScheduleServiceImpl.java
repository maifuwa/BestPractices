package com.bigboss.useramjobstore.service.impl;

import com.bigboss.useramjobstore.common.ConcisePage;
import com.bigboss.useramjobstore.common.GlobalException;
import com.bigboss.useramjobstore.domain.JobDetails;
import com.bigboss.useramjobstore.dto.JobDetailsParam;
import com.bigboss.useramjobstore.dto.JobDetailsResult;
import com.bigboss.useramjobstore.dto.JobDetailsUpdateParam;
import com.bigboss.useramjobstore.repository.JobDetailsRepository;
import com.bigboss.useramjobstore.service.ScheduleService;
import com.bigboss.useramjobstore.util.ScheduleUtil;
import com.bigboss.useramjobstore.util.SpringUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.SchedulerException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

/**
 * @author: maifuwa
 * @date: 2024/10/11 10:53
 * @description:
 */
@Transactional
@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final JobDetailsRepository jobDetailsRepository;

    @PostConstruct
    public void init() {
        jobDetailsRepository.findAll()
                .stream()
                .filter(JobDetails::getPaused)
                .forEach(jobDetails -> {
                    try {
                        ScheduleUtil.addJob(jobDetails);
                    } catch (SchedulerException e) {
                        throw GlobalException.internalServerError("初始化定时任务失败", e);
                    }
                });
    }

    @Override
    public void addJob(JobDetailsParam jobDetailsParam) throws SchedulerException {
        JobDetails jobDetails = SpringUtil.copyBean(jobDetailsParam, JobDetails.class);
        jobDetailsRepository.save(jobDetails);
        ScheduleUtil.addJob(jobDetails);
    }

    @Override
    public void deleteJob(String jobName, String jobGroup) throws SchedulerException {
        jobDetailsRepository.deleteByJobNameAndJobGroup(jobName, jobGroup);
        ScheduleUtil.deleteJob(jobName, jobGroup);
    }

    @Override
    public String pauseJob(String jobName, String jobGroup, Boolean status) throws SchedulerException {
        jobDetailsRepository.updatePausedByJobNameAndJobGroup(status, jobName, jobGroup);
        if (status) {
            ScheduleUtil.pauseJob(jobName, jobGroup);
            return "任务暂停成功";
        }

        if (ScheduleUtil.isJobExist(jobName, jobGroup)) {
            ScheduleUtil.resumeJob(jobName, jobGroup);
            return "任务恢复成功";
        }

        JobDetails jobDetails = jobDetailsRepository.findByJobNameAndJobGroup(jobName, jobGroup);
        ScheduleUtil.addJob(jobDetails);
        return "任务恢复成功";
    }

    @Override
    public void updateJob(JobDetailsUpdateParam jobDetailsUpdateParam) throws SchedulerException {
        JobDetails jobDetails = jobDetailsRepository.findByJobNameAndJobGroup(jobDetailsUpdateParam.getJobName(), jobDetailsUpdateParam.getJobGroup());
        ScheduleUtil.deleteJob(jobDetails.getJobName(), jobDetails.getJobGroup());
        updateJobDetails(jobDetails, jobDetailsUpdateParam);
        jobDetailsRepository.save(jobDetails);
        ScheduleUtil.addJob(jobDetails);
    }

    private void updateJobDetails(JobDetails jobDetails, JobDetailsUpdateParam jobDetailsUpdateParam) {
        if (StringUtils.hasText(jobDetailsUpdateParam.getNewJobName())) {
            jobDetails.setJobName(jobDetailsUpdateParam.getNewJobName());
        }
        if (StringUtils.hasText(jobDetailsUpdateParam.getNewJobGroup())) {
            jobDetails.setJobGroup(jobDetailsUpdateParam.getNewJobGroup());
        }
        if (StringUtils.hasText(jobDetailsUpdateParam.getNewInvokeTarget())) {
            jobDetails.setInvokeTarget(jobDetailsUpdateParam.getNewInvokeTarget());
        }
        if (StringUtils.hasText(jobDetailsUpdateParam.getNewCronExpression())) {
            jobDetails.setCronExpression(jobDetailsUpdateParam.getNewCronExpression());
        }
        if (StringUtils.hasText(jobDetailsUpdateParam.getNewJobDescription())) {
            jobDetails.setJobDescription(jobDetailsUpdateParam.getNewJobDescription());
        }
        if (ObjectUtils.isEmpty(jobDetailsUpdateParam.getNewConcurrent())) {
            jobDetails.setConcurrent(jobDetailsUpdateParam.getNewConcurrent());
        }
    }

    @Override
    public ConcisePage<JobDetailsResult> list(Integer page, Integer size) {
        Page<JobDetailsResult> all = jobDetailsRepository.findAll(PageRequest.of(page, size, Sort.by("id").descending()))
                .map(jobDetails -> SpringUtil.copyBean(jobDetails, JobDetailsResult.class));
        return ConcisePage.of(all.getContent(), all.getNumber(), all.getTotalPages());
    }
}
