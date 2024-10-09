package com.bigboss.usedjdbcjobstore.pojo;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

/**
 * @author: maifuwa
 * @date: 2024/10/9 11:01
 * @description:
 */
@Data
@Builder
public class JobInfoVo {

    @NotBlank
    private String jobBean;

    @NotBlank
    private String jobName;

    @NotBlank
    private String jobGroup;

    @NotBlank
    private String cronExpression;

    private Map<String, String> jobData;
}
