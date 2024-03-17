package org.bigboss.springsecuritydemo.domain;

import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;

/**
 * @author: maifuwa
 * @date: 2024/3/17 下午7:38
 * @description: 基础实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class BaseEntity {

    @CreatedDate
    private Instant createTime;

    @LastModifiedDate
    private Instant updateTime;
}
