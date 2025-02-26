package com.sky.service;

import com.sky.vo.SetmealVO;

public interface SetmealService {

    /**
     * 根据id查询套餐
     *
     * @param id 套餐id
     * @return SetmealVO套餐VO类对象
     */
    SetmealVO getSetmealVOById(Long id);
}
