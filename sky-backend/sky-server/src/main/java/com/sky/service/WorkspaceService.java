package com.sky.service;

import com.sky.vo.BusinessDataVO;
import com.sky.vo.DishOverViewVO;
import com.sky.vo.SetmealOverViewVO;

import java.time.LocalDateTime;

public interface WorkspaceService {

    /**
     * 查询今日运营数据
     *
     * @param begin 今日开始时间
     * @param end   今日结束时间
     * @return BusinessDataVO运营数据VO对象
     */
    BusinessDataVO getBusinessData(LocalDateTime begin, LocalDateTime end);

    /**
     * 查询套餐总览
     *
     * @return SetmealOverViewVO套餐数据VO对象
     */
    SetmealOverViewVO getSetmealOverView();

    /**
     * 查询菜品总览
     *
     * @return DishOverViewVO菜品数据VO对象
     */
    DishOverViewVO getDishOverView();
}
