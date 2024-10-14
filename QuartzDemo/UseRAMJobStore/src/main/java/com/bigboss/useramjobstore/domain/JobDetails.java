package com.bigboss.useramjobstore.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: maifuwa
 * @date: 2024/10/11 10:58
 * @description: JobDetails
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {@Index(columnList = "jobName,jobGroup", unique = true)})
@Entity
public class JobDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String jobName;

    @Column(nullable = false)
    private String jobGroup;

    @Column(nullable = false)
    private String invokeTarget;

    @Column(nullable = false)
    private String cronExpression;

    @Column(columnDefinition = "text")
    private String jobDescription;

    @Column(insertable = false, columnDefinition = "tinyint(1) default 0 not null comment '0 for running 1 for paused'")
    private Boolean paused;

    @Column(columnDefinition = "tinyint(1) default 1 not null comment '1 for concurrent 0 for non-concurrent'")
    private Boolean concurrent;
}
