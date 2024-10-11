package com.bigboss.useramjobstore.service.impl;

import com.bigboss.useramjobstore.common.ConcisePage;
import com.bigboss.useramjobstore.domain.JobDetails;
import com.bigboss.useramjobstore.dto.JobDetailsParam;
import com.bigboss.useramjobstore.dto.JobDetailsResult;
import com.bigboss.useramjobstore.dto.JobDetailsUpdateParam;
import com.bigboss.useramjobstore.repository.JobDetailsRepository;
import com.bigboss.useramjobstore.service.ScheduleService;
import com.bigboss.useramjobstore.util.ScheduleUtil;
import com.bigboss.useramjobstore.util.SpringUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.SchedulerException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
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

    @Override
    public void addJob(JobDetailsParam jobDetailsParam) throws SchedulerException {
        JobDetails jobDetails = SpringUtil.copyBean(jobDetailsParam, JobDetails.class);
        jobDetailsRepository.save(jobDetails);
        ScheduleUtil.addJob(jobDetails.getJobClassName(), jobDetails.getJobName(), jobDetails.getJobGroup(), jobDetails.getCronExpression(), jobDetails.getJobData());
    }

    @Override
    public void deleteJob(String jobName, String jobGroup) throws SchedulerException {
        jobDetailsRepository.deleteByJobNameAndJobGroup(jobName, jobGroup);
        ScheduleUtil.deleteJob(jobName, jobGroup);
    }

    @Override
    public void updateJob(JobDetailsUpdateParam jobDetailsUpdateParam) throws SchedulerException {
        JobDetails jobDetails = jobDetailsRepository.findByJobNameAndJobGroup(jobDetailsUpdateParam.getJobName(), jobDetailsUpdateParam.getJobGroup());
        ScheduleUtil.deleteJob(jobDetails.getJobName(), jobDetails.getJobGroup());
        updateJobDetails(jobDetails, jobDetailsUpdateParam);
        jobDetailsRepository.save(jobDetails);
        ScheduleUtil.addJob(jobDetails.getJobClassName(), jobDetails.getJobName(), jobDetails.getJobGroup(), jobDetails.getCronExpression(), jobDetails.getJobData());
    }

    private void updateJobDetails(JobDetails jobDetails, JobDetailsUpdateParam jobDetailsUpdateParam) {
        if (StringUtils.hasText(jobDetailsUpdateParam.getNewJobClassName())) {
            jobDetails.setJobClassName(jobDetailsUpdateParam.getNewJobClassName());
        }
        if (StringUtils.hasText(jobDetailsUpdateParam.getNewJobName())) {
            jobDetails.setJobName(jobDetailsUpdateParam.getNewJobName());
        }
        if (StringUtils.hasText(jobDetailsUpdateParam.getNewJobGroup())) {
            jobDetails.setJobGroup(jobDetailsUpdateParam.getNewJobGroup());
        }
        if (StringUtils.hasText(jobDetailsUpdateParam.getNewJobData())) {
            jobDetails.setJobData(jobDetailsUpdateParam.getNewJobData());
        }
        if (StringUtils.hasText(jobDetailsUpdateParam.getNewCronExpression())) {
            jobDetails.setCronExpression(jobDetailsUpdateParam.getNewCronExpression());
        }
    }

    @Override
    public ConcisePage<JobDetailsResult> list(Integer page, Integer size) {
        Page<JobDetailsResult> all = jobDetailsRepository.findAll(PageRequest.of(page, size, Sort.by("id").descending()))
                .map(jobDetails -> SpringUtil.copyBean(jobDetails, JobDetailsResult.class));
        return new ConcisePage<>(all.getContent(), all.getNumber(), all.getTotalPages());
    }
}
