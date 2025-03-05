package com.sky.mapper;

import com.sky.entity.Orders;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper {

    /**
     * 插入订单数据
     *
     * @param orders 订单实体类对象
     */
    void insert(Orders orders);
}
