package com.sky.controller.admin;

import com.sky.dto.OrdersCancelDTO;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.dto.OrdersRejectionDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.OrderService;
import com.sky.vo.OrderStatisticsVO;
import com.sky.vo.OrderVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("adminOrderController")
@Slf4j
@Api(tags = "订单相关接口")
@RequestMapping("/admin/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    /**
     * 查询订单详情
     *
     * @param id 订单id
     * @return Result类响应对象
     */
    @GetMapping("/details/{id}")
    @ApiOperation("查询订单详情")
    public Result<OrderVO> details(@PathVariable Long id) {
        OrderVO orderVO = orderService.details(id);
        return Result.success(orderVO);
    }

    /**
     * 订单条件搜索
     *
     * @param ordersPageQueryDTO 订单分页查询DTO对象
     * @return Result类响应对象
     */
    @GetMapping("/conditionSearch")
    @ApiOperation("订单条件搜索")
    public Result<PageResult> condistionSerach(OrdersPageQueryDTO ordersPageQueryDTO) {
        PageResult pageResult = orderService.conditionSearch(ordersPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 各个状态的订单数量统计
     *
     * @return Result类响应对象
     */
    @GetMapping("/statistics")
    @ApiOperation("各个状态的订单数量统计")
    public Result<OrderStatisticsVO> statistics() {
        OrderStatisticsVO orderStatisticsVO = orderService.statistics();
        return Result.success(orderStatisticsVO);
    }

    /**
     * 接单
     *
     * @param id 订单id
     * @return Result类响应对象
     */
    @PutMapping("/confirm")
    @ApiOperation("接单")
    public Result confirm(Long id) {
        orderService.confirm(id);
        return Result.success();
    }

    /**
     * 拒单
     *
     * @param ordersRejectionDTO 拒单DTO对象
     * @return Result类响应对象
     */
    @PutMapping("/rejection")
    @ApiOperation("拒单")
    public Result rejection(@RequestBody OrdersRejectionDTO ordersRejectionDTO) {
        orderService.rejection(ordersRejectionDTO);
        return Result.success();
    }

    /**
     * 取消订单
     *
     * @param ordersCancelDTO 取消订单DTO对象
     * @return Result类响应对象
     */
    @PutMapping("/cancel")
    @ApiOperation("取消订单")
    public Result cancel(OrdersCancelDTO ordersCancelDTO) {
        orderService.cancel(ordersCancelDTO);
        return Result.success();
    }
}
