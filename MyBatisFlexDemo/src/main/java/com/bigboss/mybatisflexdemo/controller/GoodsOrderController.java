package com.bigboss.mybatisflexdemo.controller;

import com.bigboss.mybatisflexdemo.common.CommonResult;
import com.bigboss.mybatisflexdemo.seriver.GoodsOrderService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author: maifuwa
 * @date: 2024/10/21 9:47
 * @description:
 */
@Validated
@RestController
@RequestMapping("/good_order")
@RequiredArgsConstructor
public class GoodsOrderController {

    private final GoodsOrderService goodsOrderService;

    @PostMapping("/add")
    public CommonResult<?> addGoodOrder(@NotNull Integer goodId, @NotBlank String totalPrice, @NotNull Integer number) {
        return CommonResult.success(goodsOrderService.save(goodId, totalPrice, number));
    }

    @DeleteMapping("/{orderId}")
    public CommonResult<?> deleteGoodOrder(@PathVariable Integer orderId) {
        return CommonResult.success(goodsOrderService.delete(orderId));
    }

    @PostMapping("/update")
    public CommonResult<?> updateGoodOrder(@NotNull Integer orderId, String totalPrice, Integer number) {
        return CommonResult.success(goodsOrderService.update(orderId, totalPrice, number));
    }

    @GetMapping("/{orderId}")
    public CommonResult<?> getGoodOrder(@PathVariable Integer orderId) {
        return CommonResult.success(goodsOrderService.getById(orderId));
    }

    @GetMapping("/list/{page}/{size}")
    public CommonResult<?> listGoodOrder(@PathVariable Integer page, @PathVariable Integer size) {
        return CommonResult.success(goodsOrderService.list(page, size));
    }
}
