package com.bigboss.useramjobstore.service.impl;

import com.bigboss.useramjobstore.common.ConcisePage;
import com.bigboss.useramjobstore.common.GlobalException;
import com.bigboss.useramjobstore.domain.JobDetails;
import com.bigboss.useramjobstore.dto.JobDetailsParam;
import com.bigboss.useramjobstore.dto.JobDetailsResult;
import com.bigboss.useramjobstore.dto.JobDetailsUpdateParam;
import com.bigboss.useramjobstore.repository.JobDetailsRepository;
import com.bigboss.useramjobstore.service.ScheduleService;
import com.bigboss.useramjobstore.util.JobInvokeUtil;
import com.bigboss.useramjobstore.util.ScheduleUtil;
import com.bigboss.useramjobstore.util.SpringUtil;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.SchedulerException;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Optional;

/**
 * @author: maifuwa
 * @date: 2024/10/11 10:53
 * @description: 定时任务服务实现
 */
@Transactional
@Slf4j
@Service
@DependsOn("scheduleUtil")
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final JobDetailsRepository jobDetailsRepository;

    @PostConstruct
    public void init() {
        log.info("初始化定时任务");
        jobDetailsRepository.findAll()
                .stream()
                .filter(jobDetails -> !jobDetails.getPaused())
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
        addJobInspection(jobDetailsParam.getInvokeTarget());

        JobDetails jobDetails = SpringUtil.copyBean(jobDetailsParam, JobDetails.class);
        jobDetailsRepository.save(jobDetails);
        ScheduleUtil.addJob(jobDetails);
    }

    private boolean addJobInspection(String invokeTarget) {
        if (!SpringUtil.isBeanHasMethod(JobInvokeUtil.getBeanName(invokeTarget), JobInvokeUtil.getMethodName(invokeTarget),
                JobInvokeUtil.getMethodParamTypes(JobInvokeUtil.getMethodParams(invokeTarget)))) {
            throw GlobalException.badRequest("invokeTarget 配置错误");
        }
        return true;
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
        Optional.ofNullable(jobDetailsUpdateParam.getNewJobName())
                .filter(StringUtils::hasText)
                .ifPresent(jobDetails::setJobName);

        Optional.ofNullable(jobDetailsUpdateParam.getNewJobGroup())
                .filter(StringUtils::hasText)
                .ifPresent(jobDetails::setJobGroup);

        Optional.ofNullable(jobDetailsUpdateParam.getNewInvokeTarget())
                .filter(StringUtils::hasText)
                .filter(this::addJobInspection)
                .ifPresent(jobDetails::setInvokeTarget);

        Optional.ofNullable(jobDetailsUpdateParam.getNewCronExpression())
                .filter(StringUtils::hasText)
                .ifPresent(jobDetails::setCronExpression);

        Optional.ofNullable(jobDetailsUpdateParam.getNewJobDescription())
                .filter(StringUtils::hasText)
                .ifPresent(jobDetails::setJobDescription);

        Optional.ofNullable(jobDetailsUpdateParam.getNewConcurrent())
                .filter(ObjectUtils::isEmpty)
                .ifPresent(jobDetails::setConcurrent);
    }

    @Override
    public ConcisePage<JobDetailsResult> list(Integer page, Integer size) {
        Page<JobDetailsResult> all = jobDetailsRepository.findAll(PageRequest.of(page, size, Sort.by("id").descending()))
                .map(jobDetails -> SpringUtil.copyBean(jobDetails, JobDetailsResult.class));
        return ConcisePage.of(all.getContent(), all.getNumber(), all.getTotalPages());
    }
}
