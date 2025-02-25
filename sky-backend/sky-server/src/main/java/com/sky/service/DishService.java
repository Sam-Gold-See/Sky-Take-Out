package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;

import java.util.List;

public interface DishService {

    /**
     * 新增菜品和相对应的口味
     *
     * @param dishDTO 菜品DTO对象
     */
    void addDishWithFlavors(DishDTO dishDTO);

    /**
     * 菜品分页查询
     *
     * @param dishPageQueryDTO 菜品分页查询
     * @return PageResult类分页查询响应对象
     */
    PageResult getDishListPage(DishPageQueryDTO dishPageQueryDTO);

    /**
     * 菜品的批量删除
     *
     * @param ids 菜品id集合
     */
    void deleteDishes(List<Long> ids);
}
