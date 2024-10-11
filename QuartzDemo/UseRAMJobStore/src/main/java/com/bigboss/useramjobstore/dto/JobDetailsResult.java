package com.bigboss.useramjobstore.dto;

import lombok.Data;

/**
 * @author: maifuwa
 * @date: 2024/10/11 16:00
 * @description:
 */
@Data
public class JobDetailsResult {

    private String jobClassName;

    private String jobName;

    private String jobGroup;

    private String cronExpression;

    private String jobData;

    private String jobDescription;
}
