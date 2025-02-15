package com.bigboss.mybatisflexdemo.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import java.io.Serializable;
import java.math.BigDecimal;

import java.io.Serial;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商品 实体类。
 *
 * @author maifu
 * @since 2024-10-21
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "goods", dataSource = "postgres_one")
public class Goods implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id(keyType = KeyType.Auto)
    private Integer id;

    /**
     * 商品名字
     */
    private String name;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 逻辑删除字段
     */
    @Column(isLogicDelete = true)
    private Boolean isDelete;

}
