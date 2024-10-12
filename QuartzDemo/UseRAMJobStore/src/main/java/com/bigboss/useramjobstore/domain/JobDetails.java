package com.bigboss.useramjobstore.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: maifuwa
 * @date: 2024/10/11 10:58
 * @description:
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
        @Index(columnList = "jobName,jobGroup", unique = true)
})
@Entity
public class JobDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String jobClassName;

    @Column(nullable = false)
    private String jobName;

    @Column(nullable = false)
    private String jobGroup;

    @Column(nullable = false)
    private String cronExpression;

    @Column(nullable = false, columnDefinition = "tinyint(1) default 0")
    private Boolean paused = false;

    private String jobData;

    private String jobDescription;
}
