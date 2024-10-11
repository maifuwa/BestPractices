package com.bigboss.useramjobstore.controller;

import com.bigboss.useramjobstore.common.CommonResult;
import com.bigboss.useramjobstore.dto.JobDetailsParam;
import com.bigboss.useramjobstore.dto.JobDetailsUpdateParam;
import com.bigboss.useramjobstore.service.ScheduleService;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.quartz.SchedulerException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author: maifuwa
 * @date: 2024/10/11 10:52
 * @description:
 */
@Validated
@RestController
@RequestMapping("/schedule")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping("/add")
    public CommonResult<?> add(@RequestBody JobDetailsParam jobDetailsParam) throws SchedulerException {
        scheduleService.addJob(jobDetailsParam);
        return CommonResult.success("任务添加成功");
    }

    @DeleteMapping("/delete")
    public CommonResult<?> delete(@NotBlank String jobName, @NotBlank String jobGroup) throws SchedulerException {
        scheduleService.deleteJob(jobName, jobGroup);
        return CommonResult.success("任务删除成功");
    }

    @PostMapping("/update")
    public CommonResult<?> update(@RequestBody JobDetailsUpdateParam jobDetailsUpdateParam) throws SchedulerException {
        scheduleService.updateJob(jobDetailsUpdateParam);
        return CommonResult.success("任务更新成功");
    }

    @GetMapping("/list/{page}/{size}")
    public CommonResult<?> list(@PathVariable Integer page, @PathVariable Integer size) {
        return CommonResult.success(scheduleService.list(page, size));
    }
}
