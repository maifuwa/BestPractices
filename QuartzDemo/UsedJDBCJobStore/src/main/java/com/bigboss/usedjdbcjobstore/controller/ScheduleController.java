package com.bigboss.usedjdbcjobstore.controller;

import com.bigboss.usedjdbcjobstore.Service.ScheduleService;
import com.bigboss.usedjdbcjobstore.common.CommonResult;
import com.bigboss.usedjdbcjobstore.common.GlobalException;
import lombok.RequiredArgsConstructor;
import org.quartz.SchedulerException;
import org.springframework.web.bind.annotation.*;

/**
 * @description:
 * @author: maifuwa
 * @date: 2024/10/7 10:53
 */
@RestController
@RequestMapping("/schedule")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping("/add")
    public CommonResult<?> addJob(String jobBean, String jobName, String jobGroup, String cronExpression) throws SchedulerException, ClassNotFoundException {
        return CommonResult.success(scheduleService.addJob(jobBean, jobName, jobGroup, cronExpression));
    }

    @DeleteMapping("/delete")
    public CommonResult<?> delete() {
        throw new GlobalException("删除失败");
    }

    @PostMapping("/update")
    public CommonResult<?> update() {
        throw new RuntimeException("更新失败");
    }

    @GetMapping("/list")
    public CommonResult<?> list() {
        return CommonResult.success("查询成功");
    }
}
