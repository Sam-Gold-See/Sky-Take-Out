package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.entity.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface OrderMapper {

    /**
     * 插入订单数据
     *
     * @param orders 订单实体类对象
     */
    void insert(Orders orders);


    /**
     * 根据订单号查询订单
     *
     * @param orderNumber 订单编号
     */
    @Select("select * from orders where number = #{orderNumber}")
    Orders getByNumber(String orderNumber);

    /**
     * 修改订单信息
     *
     * @param orders 订单对象
     */
    void update(Orders orders);

    /**
     * 分页查询历史订单
     *
     * @param ordersPageQueryDTO 订单分页查询DTO对象
     * @return Page<Orders>订单分页对象
     */
    Page<Orders> getListPage(OrdersPageQueryDTO ordersPageQueryDTO);
}
