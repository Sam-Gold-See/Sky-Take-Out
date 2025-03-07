package com.sky.service.impl;

import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.UserMapper;
import com.sky.service.WorkspaceService;
import com.sky.vo.BusinessDataVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class WorkspaceServiceImpl implements WorkspaceService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    private OrderMapper orderMapper;

    /**
     * 查询今日运营数据
     *
     * @return BusinessDataVO运营数据VO对象
     */
    @Override
    public BusinessDataVO getBusinessData() {
        LocalDate date = LocalDate.now();
        LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
        LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);

        Map newUserListMap = new HashMap();
        newUserListMap.put("begin", beginTime);
        newUserListMap.put("end", endTime);
        Integer newUserCount = userMapper.countByMap(newUserListMap);
        newUserCount = newUserCount == null ? 0 : newUserCount;

        Map orderCountMap = new HashMap();
        orderCountMap.put("begin", beginTime);
        orderCountMap.put("end", endTime);
        Integer orderCount = orderMapper.countByMap(orderCountMap);
        orderCount = orderCount == null ? 0 : orderCount;

        Map validOrderCountMap = new HashMap();
        validOrderCountMap.put("begin", beginTime);
        validOrderCountMap.put("end", endTime);
        validOrderCountMap.put("status", Orders.COMPLETED);
        Integer validOrderCount = orderMapper.countByMap(validOrderCountMap);
        validOrderCount = validOrderCount == null ? 0 : validOrderCount;

        Double orderCompletionRate = orderCount == 0 ? 0 : (double) validOrderCount / orderCount;

        Map turnoverMap = new HashMap();
        turnoverMap.put("begin", beginTime);
        turnoverMap.put("end", endTime);
        turnoverMap.put("status", Orders.COMPLETED);
        Double turnover = orderMapper.sumByMap(turnoverMap);
        turnover = turnover == null ? 0 : turnover;

        Double unitPrice = validOrderCount == 0 ? 0 : (double) turnover / validOrderCount;

        return BusinessDataVO.builder()
                .newUsers(newUserCount)
                .orderCompletionRate(orderCompletionRate)
                .turnover(turnover)
                .unitPrice(unitPrice)
                .validOrderCount(validOrderCount)
                .build();
    }
}
