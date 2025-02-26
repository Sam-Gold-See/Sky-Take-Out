package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.vo.SetmealVO;

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
}
