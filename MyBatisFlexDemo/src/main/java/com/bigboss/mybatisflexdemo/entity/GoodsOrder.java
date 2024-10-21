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
 * 订单 实体类。
 *
 * @author maifu
 * @since 2024-10-21
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "goods_order", dataSource = "postgres_two")
public class GoodsOrder implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id(keyType = KeyType.Auto)
    private Integer id;

    /**
     * 商品
     */
    private Integer goodsId;

    /**
     * 总价
     */
    private BigDecimal totalPrice;

    /**
     * 个数
     */
    private Integer number;

    /**
     * 逻辑删除字段
     */
    @Column(isLogicDelete = true)
    private Boolean isDelete;

}
