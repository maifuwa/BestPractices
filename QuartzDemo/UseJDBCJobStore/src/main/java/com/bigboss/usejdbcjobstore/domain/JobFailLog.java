package com.bigboss.usejdbcjobstore.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * @author: maifuwa
 * @date: 2024/10/12 16:02
 * @description:
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table
@Entity
@EntityListeners(AuditingEntityListener.class)
public class JobFailLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String jobName;

    private String jobGroup;

    private String failMessage;

    @CreatedDate
    private LocalDateTime createTime;
}
