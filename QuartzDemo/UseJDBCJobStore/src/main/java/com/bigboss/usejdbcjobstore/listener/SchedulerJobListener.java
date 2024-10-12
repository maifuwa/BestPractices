package com.bigboss.usejdbcjobstore.listener;

import com.bigboss.usejdbcjobstore.domain.JobFailLog;
import com.bigboss.usejdbcjobstore.repository.JobFailLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.stereotype.Component;

/**
 * @author: maifuwa
 * @date: 2024/10/12 15:53
 * @description:
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SchedulerJobListener implements JobListener {

    private final JobFailLogRepository jobFailLogRepository;

    @Override
    public String getName() {
        return getClass().getName();
    }

    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {
    }

    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        if (jobException != null) {
            JobFailLog jobFailLog = JobFailLog.builder()
                    .jobName(context.getJobDetail().getKey().getName())
                    .jobGroup(context.getJobDetail().getKey().getGroup())
                    .failMessage(jobException.getMessage())
                    .build();
            jobFailLogRepository.save(jobFailLog);
        }
    }

}
