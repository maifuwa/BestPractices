package com.bigboss.usejdbcjobstore.controller;

import com.bigboss.usejdbcjobstore.pojo.JobInfoVo;
import com.bigboss.usejdbcjobstore.pojo.NewJobInfoVo;
import com.bigboss.usejdbcjobstore.service.ScheduleService;
import com.bigboss.usejdbcjobstore.common.CommonResult;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.quartz.SchedulerException;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Optional;

/**
 * @author: maifuwa
 * @date: 2024/10/7 10:53
 * @description: 任务调度控制器
 */
@Validated
@RestController
@RequestMapping("/schedule")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping("/add")
    public CommonResult<?> add(@RequestBody JobInfoVo jobInfoVo) throws SchedulerException {
        return CommonResult.success(scheduleService.addJob(jobInfoVo.getJobBean(), jobInfoVo.getJobName(), jobInfoVo.getJobGroup(), jobInfoVo.getCronExpression(), Optional.ofNullable(jobInfoVo.getJobData()).orElse(Collections.emptyMap())));
    }

    @DeleteMapping("/delete")
    public CommonResult<?> delete(@NotBlank String jobName, @NotBlank String jobGroup) throws SchedulerException {
        return CommonResult.success(scheduleService.deleteJob(jobName, jobGroup));
    }

    @GetMapping("/pause/{status}")
    public CommonResult<?> pause(@NotBlank String jobName, @NotBlank String jobGroup, @PathVariable @NotNull Boolean status) throws SchedulerException {
        return CommonResult.success(scheduleService.pauseJob(jobName, jobGroup, status));
    }

    @PostMapping("/update")
    public CommonResult<?> update(@RequestBody NewJobInfoVo newJobInfoVo) throws SchedulerException {
        String jobName = StringUtils.hasText(newJobInfoVo.getNewJobName()) ? newJobInfoVo.getNewJobName() : newJobInfoVo.getJobName();
        String jobGroup = StringUtils.hasText(newJobInfoVo.getNewJobGroup()) ? newJobInfoVo.getNewJobGroup() : newJobInfoVo.getJobGroup();

        if (!jobName.equals(newJobInfoVo.getJobName()) || !jobGroup.equals(newJobInfoVo.getJobGroup())) {
            scheduleService.updateJobKey(newJobInfoVo.getJobName(), newJobInfoVo.getJobGroup(), jobName, jobGroup);
        }
        if (StringUtils.hasText(newJobInfoVo.getNewCronExpression())) {
            scheduleService.updateJobCron(jobName, jobGroup, newJobInfoVo.getNewCronExpression());
        }
        if (newJobInfoVo.getNewJobData() != null) {
            scheduleService.updateJobData(jobName, jobGroup, newJobInfoVo.getNewJobData());
        }

        return CommonResult.success("任务更新成功");
    }


    @GetMapping("/list")
    public CommonResult<?> list() throws SchedulerException {
        return CommonResult.success(scheduleService.list());
    }
}
