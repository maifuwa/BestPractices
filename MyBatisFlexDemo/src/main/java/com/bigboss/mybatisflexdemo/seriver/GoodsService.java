package com.bigboss.mybatisflexdemo.seriver;

import com.bigboss.mybatisflexdemo.entity.Goods;
import com.bigboss.mybatisflexdemo.mapper.GoodsMapper;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.If;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.update.UpdateChain;
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

    public String deleteGoods(Integer id) {
        goodsMapper.deleteById(id);
        return "删除成功";
    }

    public String updateGoods(Integer id, String name, String price) {
        UpdateChain.of(Goods.class)
                .set(Goods::getName, name, If::hasText)
                .set(Goods::getPrice, new BigDecimal(price), If::notNull)
                .where(Goods::getId).eq(id)
                .update();
        return "更新成功";
    }

    public Goods getGood(Integer id) {
        return goodsMapper.selectOneById(id);
    }

    public Page<Goods> listGoods(Integer page, Integer size) {
        return goodsMapper.paginate(page, size, QueryWrapper.create());
    }

}
