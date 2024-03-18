package org.bigboss.springsecuritydemo.domain;

import jakarta.persistence.*;
import lombok.*;

/**
 * @author: maifuwa
 * @date: 2024/3/17 下午7:40
 * @description: 角色实体类
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Role extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
}
