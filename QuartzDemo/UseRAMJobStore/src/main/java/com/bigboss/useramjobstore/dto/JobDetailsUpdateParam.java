package com.bigboss.useramjobstore.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author: maifuwa
 * @date: 2024/10/11 15:22
 * @description:
 */
@Data
public class JobDetailsUpdateParam {

    @NotBlank
    private String jobName;

    @NotBlank
    private String jobGroup;

    private String newJobClassName;

    private String newJobName;

    private String newJobGroup;

    private String newCronExpression;

    private String newJobData;

    private String newJobDescription;
}
