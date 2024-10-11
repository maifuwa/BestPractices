package com.bigboss.usejdbcjobstore.pojo;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Map;

/**
 * @author: maifuwa
 * @date: 2024/10/9 9:51
 * @description:
 */
@Data
public class NewJobInfoVo {

    @NotBlank
    private String jobName;

    @NotBlank
    private String jobGroup;

    private String newJobName;

    private String newJobGroup;

    private String newCronExpression;

    private Map<String, String> newJobData;
}
