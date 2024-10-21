package com.bigboss.mybatisflexdemo.seriver;

import com.bigboss.mybatisflexdemo.entity.GoodsOrder;
import com.bigboss.mybatisflexdemo.mapper.GoodsOrderMapper;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author: maifuwa
 * @date: 2024/10/21 9:48
 * @description:
 */
@Service
@RequiredArgsConstructor
public class GoodsOrderService {

    private final GoodsOrderMapper goodsOrderMapper;

    public GoodsOrder save(Integer goodId, String totalPrice, Integer number) {
        GoodsOrder order = GoodsOrder.builder()
                .goodsId(goodId)
                .totalPrice(new BigDecimal(totalPrice))
                .number(number)
                .build();
        goodsOrderMapper.insertSelective(order);
        return order;
    }

    public String delete(Integer orderId) {
        goodsOrderMapper.deleteById(orderId);
        return "删除成功";
    }

    public GoodsOrder update(Integer orderId, String totalPrice, Integer number) {
        GoodsOrder newGoodsOrder = GoodsOrder.builder()
                .id(orderId)
                .totalPrice(new BigDecimal(totalPrice))
                .number(number)
                .build();
        goodsOrderMapper.insertOrUpdateSelective(newGoodsOrder);
        return newGoodsOrder;
    }

    public GoodsOrder getById(Integer orderId) {
        return goodsOrderMapper.selectOneById(orderId);
    }

    public Page<GoodsOrder> list(Integer page, Integer size) {
        return goodsOrderMapper.paginate(page, size, QueryWrapper.create());
    }
}
