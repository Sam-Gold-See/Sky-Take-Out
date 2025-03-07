package com.sky.service.impl;

import com.sky.constant.StatusConstant;
import com.sky.entity.Orders;
import com.sky.mapper.DishMapper;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.mapper.UserMapper;
import com.sky.service.WorkspaceService;
import com.sky.vo.BusinessDataVO;
import com.sky.vo.DishOverViewVO;
import com.sky.vo.SetmealOverViewVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class WorkspaceServiceImpl implements WorkspaceService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private SetmealMapper setmealMapper;

    @Autowired
    private DishMapper dishMapper;

    /**
     * 查询今日运营数据
     *
     * @return BusinessDataVO运营数据VO对象
     */
    @Override
    public BusinessDataVO getBusinessData(LocalDateTime begin, LocalDateTime end) {

        Map map = new HashMap();
        map.put("begin", begin);
        map.put("end", end);

        Integer newUserCount = userMapper.countByMap(map);

        Integer orderCount = orderMapper.countByMap(map);

        map.put("status", Orders.COMPLETED);

        Integer validOrderCount = orderMapper.countByMap(map);

        Double turnover = orderMapper.sumByMap(map);
        turnover = turnover == null ? 0 : turnover;

        Double orderCompletionRate = orderCount == 0 ? 0 : (double) validOrderCount / orderCount;

        Double unitPrice = validOrderCount == 0 ? 0 : turnover / validOrderCount;

        return BusinessDataVO.builder()
                .newUsers(newUserCount)
                .orderCompletionRate(orderCompletionRate)
                .turnover(turnover)
                .unitPrice(unitPrice)
                .validOrderCount(validOrderCount)
                .build();
    }

    /**
     * 查询套餐总览
     *
     * @return SetmealOverViewVO套餐数据VO对象
     */
    public SetmealOverViewVO getSetmealOverView() {
        Map map = new HashMap();
        map.put("status", StatusConstant.ENABLE);
        Integer sold = setmealMapper.countByMap(map);

        map.put("status", StatusConstant.DISABLE);
        Integer discontinued = setmealMapper.countByMap(map);

        return SetmealOverViewVO.builder()
                .sold(sold)
                .discontinued(discontinued)
                .build();
    }

    /**
     * 查询菜品总览
     *
     * @return DishOverViewVO菜品数据VO对象
     */
    @Override
    public DishOverViewVO getDishOverView() {
        Map map = new HashMap();
        map.put("status", StatusConstant.ENABLE);
        Integer sold = dishMapper.countByMap(map);

        map.put("status", StatusConstant.DISABLE);
        Integer discontinued = dishMapper.countByMap(map);

        return DishOverViewVO.builder()
                .sold(sold)
                .discontinued(discontinued)
                .build();
    }
}
