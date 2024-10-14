package com.bigboss.useramjobstore.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * @author: maifuwa
 * @date: 2024/10/11 14:49
 * @description:
 */
@Data
public class JobDetailsParam {

    @NotBlank
    @Length(min = 1, max = 255)
    private String jobName;

    @NotBlank
    @Length(min = 1, max = 255)
    private String jobGroup;

    @NotBlank
    @Length(min = 1, max = 255)
    private String invokeTarget;

    @NotBlank
    private String cronExpression;

    private String jobDescription;

    private Boolean concurrent = Boolean.TRUE;
}
