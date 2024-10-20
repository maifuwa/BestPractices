package com.bigboss.mybatisflexdemo.controller;

import com.bigboss.mybatisflexdemo.common.CommonResult;
import com.bigboss.mybatisflexdemo.seriver.GoodsService;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author: maifuwa
 * @date: 2024/10/20 19:51
 * @description:
 */
@Validated
@RestController
@RequestMapping("/goods")
@RequiredArgsConstructor
public class GoodsController {

    private final GoodsService goodsService;

    @PostMapping("/add")
    public CommonResult<?> addGoods(@NotBlank String name, @NotBlank String price) {
        return CommonResult.success(goodsService.addGoods(name, price));
    }

    @GetMapping("/{id}")
    public CommonResult<?> getGood(@PathVariable Integer id) {
        return CommonResult.success(goodsService.getGood(id));
    }

    @GetMapping("/list/{pageNum}/{pageSize}")
    public CommonResult<?> listGoods(@PathVariable Integer pageNum, @PathVariable Integer pageSize) {
        return CommonResult.success(goodsService.listGoods(pageNum, pageSize));
    }
}
