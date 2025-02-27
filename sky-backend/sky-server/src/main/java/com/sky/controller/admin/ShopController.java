package com.sky.controller.admin;

import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/shop")
@Slf4j
@Api("店铺相关接口")
public class ShopController {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 设置店铺营业状态
     *
     * @param status 店铺的营业情况
     * @return Result类响应对象
     */
    @PutMapping("/{status}")
    @ApiOperation("设置店铺的营业情况")
    public Result setStatus(@PathVariable Integer status) {
        redisTemplate.opsForValue().set("SHOP_STATUS", status);
        return Result.success();
    }
}
