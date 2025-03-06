package com.sky.service;

import com.sky.dto.OrdersPageQueryDTO;
import com.sky.dto.OrdersPaymentDTO;
import com.sky.dto.OrdersSubmitDTO;
import com.sky.result.PageResult;
import com.sky.vo.OrderPaymentVO;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;

public interface OrderService {

    /**
     * 用户下单
     *
     * @param ordersSubmitDTO 用户订单提交DTO对象
     * @return OrderSubmitVO 订单提交VO对象
     */
    OrderSubmitVO submit(OrdersSubmitDTO ordersSubmitDTO);

    /**
     * 订单支付
     *
     * @param ordersPaymentDTO 用户订单支付DTO对象
     * @return OrderPaymentVO 订单支付VO对象
     */
    OrderPaymentVO payment(OrdersPaymentDTO ordersPaymentDTO) throws Exception;

    /**
     * 支付成功，修改订单状态
     *
     * @param outTradeNo 交易情况
     */
    void paySuccess(String outTradeNo);

    /**
     * 历史订单分页查询
     *
     * @param ordersPageQueryDTO 订单分页查询DTO对象
     * @return PageResult类响应对象
     */
    PageResult getListPage(OrdersPageQueryDTO ordersPageQueryDTO);

    /**
     * 查询订单详情
     *
     * @param id 订单id
     * @return OrderVO订单VO类对象
     */
    OrderVO details(Long id);

    /**
     * 取消订单
     *
     * @param id 订单id
     */
    void cancel(Long id) throws Exception;

    /**
     * 再来一单
     *
     * @param id 订单id
     */
    void repetition(Long id);

    /**
     * 订单条件搜索
     *
     * @param ordersPageQueryDTO 订单分页查询DTO对象
     * @return PageResult类响应对象
     */
    PageResult conditionSearch(OrdersPageQueryDTO ordersPageQueryDTO);
}
