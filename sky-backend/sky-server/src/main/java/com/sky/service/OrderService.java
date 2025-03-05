package com.sky.service;

import com.sky.dto.OrdersSubmitDTO;
import com.sky.vo.OrderSubmitVO;

public interface OrderService {

    /**
     * 用户下单
     *
     * @param ordersSubmitDTO 用户订单提交DTO对象
     * @return OrderSubmitVO 订单提交VO对象
     */
    OrderSubmitVO submit(OrdersSubmitDTO ordersSubmitDTO);
}
