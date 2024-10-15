package com.bigboss.useramjobstore.dto;

import lombok.Data;

/**
 * @author: maifuwa
 * @date: 2024/10/11 16:00
 * @description: 定时任务详情查询结果
 */
@Data
public class JobDetailsResult {

    private String jobName;

    private String jobGroup;

    private String invokeTarget;

    private String cronExpression;

    private String jobDescription;

    private Boolean concurrent;

    private Boolean paused;
}
