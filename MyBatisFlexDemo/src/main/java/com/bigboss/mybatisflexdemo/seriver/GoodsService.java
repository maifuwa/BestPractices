package com.bigboss.mybatisflexdemo.seriver;

import com.bigboss.mybatisflexdemo.entity.Goods;
import com.bigboss.mybatisflexdemo.mapper.GoodsMapper;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author: maifuwa
 * @date: 2024/10/16 12:55
 * @description:
 */
@Service
@RequiredArgsConstructor
public class GoodsService {

    private final GoodsMapper goodsMapper;

    public Goods addGoods(String name, String price) {
        Goods goods = Goods.builder()
                .name(name)
                .price(new BigDecimal(price))
                .build();
        goodsMapper.insertSelective(goods);
        return goods;
    }

    public Goods getGood(Integer id) {
        return goodsMapper.selectOneById(id);
    }

    public Page<Goods> listGoods(Integer pageNum, Integer pageSize) {
        return goodsMapper.paginate(pageNum, pageSize, QueryWrapper.create());
    }
}
