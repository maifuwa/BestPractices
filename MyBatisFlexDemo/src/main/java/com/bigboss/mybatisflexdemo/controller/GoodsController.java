package com.bigboss.mybatisflexdemo.controller;

import com.bigboss.mybatisflexdemo.common.CommonResult;
import com.bigboss.mybatisflexdemo.seriver.GoodsService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @DeleteMapping("/{id}")
    public CommonResult<?> deleteGoods(@PathVariable Integer id) {
        return CommonResult.success(goodsService.deleteGoods(id));
    }

    @PostMapping("/update")
    public CommonResult<?> updateGoods(@NotNull Integer id, String name, String price) {
        return CommonResult.success(goodsService.updateGoods(id, name, price));
    }

    @GetMapping("/{id}")
    public CommonResult<?> getGoods(@PathVariable Integer id) {
        return CommonResult.success(goodsService.getGood(id));
    }

    @GetMapping("/list/{page}/{size}")
    public CommonResult<?> listGoods(@PathVariable Integer page, @PathVariable Integer size) {
        return CommonResult.success(goodsService.listGoods(page, size));
    }
}
