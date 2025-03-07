package com.sky.service;

import com.sky.vo.BusinessDataVO;
import com.sky.vo.SetmealOverViewVO;

public interface WorkspaceService {

    /**
     * 查询今日运营数据
     *
     * @return BusinessDataVO运营数据VO对象
     */
    BusinessDataVO getBusinessData();

    /**
     * 查询套餐总览
     *
     * @return SetmealOverViewVO套餐数据VO对象
     */
    SetmealOverViewVO getSetmealOverView();
}
