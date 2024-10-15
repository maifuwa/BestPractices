package com.bigboss.useramjobstore.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author: maifuwa
 * @date: 2024/10/11 15:22
 * @description: 定时任务详情更新参数
 */
@Data
public class JobDetailsUpdateParam {

    @NotBlank
    private String jobName;

    @NotBlank
    private String jobGroup;

    private String newJobName;

    private String newJobGroup;

    private String newInvokeTarget;

    private String newCronExpression;

    private String newJobDescription;

    private Boolean newConcurrent;
}
