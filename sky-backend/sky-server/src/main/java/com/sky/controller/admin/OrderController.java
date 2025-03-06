package com.sky.controller.admin;

import com.sky.dto.OrdersPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.OrderService;
import com.sky.vo.OrderVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
