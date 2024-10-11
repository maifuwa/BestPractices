package com.bigboss.usejdbcjobstore.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: maifuwa
 * @date: 2024/10/8 20:40
 * @description: 用户实体类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    private String name;

    private Integer age;

}
