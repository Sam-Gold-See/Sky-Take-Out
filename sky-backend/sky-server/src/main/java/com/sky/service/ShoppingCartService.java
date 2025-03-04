package com.sky.service;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;

import java.util.List;

public interface ShoppingCartService {


    /**
     * 添加购物车
     *
     * @param shoppingCartDTO 购物车DTO对象
     */
    void addShoppingCart(ShoppingCartDTO shoppingCartDTO);

    /**
     * 查询购物车列表
     *
     * @return List<ShoppingCart>购物车数据集合类
     */
    List<ShoppingCart> getShoppingCartList();
}
