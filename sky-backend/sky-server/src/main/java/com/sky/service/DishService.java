package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

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

    /**
     * 根据id查询菜品和口味数据
     *
     * @param id 菜品id
     * @return DishVO菜品VO对象类
     */
    DishVO getDishVOById(Long id);

    /**
     * 启用禁用菜品
     *
     * @param status 目标启用/禁用状态信息
     * @param id     菜品id
     */
    void startOrStopDish(Integer status, Long id);
}
