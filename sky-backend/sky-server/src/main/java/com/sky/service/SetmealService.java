package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.vo.SetmealVO;

import java.util.List;

public interface SetmealService {

    /**
     * 根据id查询套餐
     *
     * @param id 套餐id
     * @return SetmealVO套餐VO类对象
     */
    SetmealVO getSetmealVOById(Long id);

    /**
     * 新增套餐
     *
     * @param setmealDTO 套餐DTO类对象
     */
    void insertSetmeal(SetmealDTO setmealDTO);

    /**
     * 套餐分页查询
     *
     * @param setmealPageQueryDTO 套餐分页查询DTO类对象
     * @return PageResult类分页查询对象
     */
    PageResult getSetmealListPage(SetmealPageQueryDTO setmealPageQueryDTO);

    /**
     * 起售/停售套餐
     *
     * @param status 目标套餐起售停售状态
     * @param id     目标套餐id
     */
    void startOrStopSetmeal(Integer status, Long id);

    /**
     * 根据id批量删除套餐
     *
     * @param ids 套餐id集合类
     */
    void deleteSetmealByIds(List<Long> ids);
}
