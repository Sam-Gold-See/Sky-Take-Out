package com.sky.mapper;

import com.sky.entity.OrderDetail;
import com.sky.entity.Orders;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderDetailMapper {

    /**
     * 插入批量订单明细数据
     *
     * @param orderDetailList 订单明细数据集合类
     */
    void insertBatch(List<OrderDetail> orderDetailList);

    /**
     * 动态查询订单
     *
     * @param orders 目标订单类型
     */
    List<OrderDetail> list(Orders orders);
}
