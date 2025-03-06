package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.entity.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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
     * 动态修改订单信息
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

    /**
     * 动态查询订单
     *
     * @param orders 订单实体对象
     * @return Orders类实体对象
     */
    List<Orders> list(Orders orders);

    /**
     * 查询指定状态订单数
     *
     * @param status 指定状态
     * @return 订单数
     */
    @Select("select count(id) from orders where status = #{status}")
    Integer count(Integer status);

    /**
     * 根据状态和订单时间查询订单
     *
     * @param status    订单状态
     * @param orderTime 订单时间
     * @return List<Orders>订单集合类
     */
    @Select("select * from orders where status = #{status} and order_time < #{orderTime}")
    List<Orders> getByStatusAndOrderTimeLT(Integer status, LocalDateTime orderTime);

    /**
     * 根据动态条件统计营业额数据
     *
     * @param map 参数列表
     * @return Double类营业额
     */
    Double sumByMap(Map map);
}
