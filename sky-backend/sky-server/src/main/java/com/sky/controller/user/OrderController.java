package com.sky.controller.user;

import com.sky.dto.OrdersPageQueryDTO;
import com.sky.dto.OrdersPaymentDTO;
import com.sky.dto.OrdersSubmitDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.OrderService;
import com.sky.vo.OrderPaymentVO;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("userOrderController")
@Api(tags = "C端订单相关接口")
@Slf4j
@RequestMapping("/user/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    /**
     * 用户下单
     *
     * @param ordersSubmitDTO 用户订单提交DTO对象
     * @return Result类响应对象
     */
    @ApiOperation("用户下单")
    @PostMapping("/submit")
    public Result<OrderSubmitVO> submit(@RequestBody OrdersSubmitDTO ordersSubmitDTO) {
        OrderSubmitVO orderSubmitVO = orderService.submit(ordersSubmitDTO);
        return Result.success(orderSubmitVO);
    }

    /**
     * 订单支付
     *
     * @param ordersPaymentDTO 用户订单支付DTO对象
     * @return Result类响应对象
     */
    @PutMapping("/payment")
    @ApiOperation("订单支付")
    public Result<OrderPaymentVO> payment(@RequestBody OrdersPaymentDTO ordersPaymentDTO) throws Exception {
        log.info("订单支付：{}", ordersPaymentDTO);
        OrderPaymentVO orderPaymentVO = orderService.payment(ordersPaymentDTO);
        log.info("生成预支付交易单：{}", orderPaymentVO);
        return Result.success(orderPaymentVO);
    }

    /**
     * 历史订单分页查询
     *
     * @param page     页数
     * @param pageSize 页面大小
     * @param status   订单状态 1待付款 2待接单 3已接单 4派送中 5已完成 6已取消
     * @return Result类响应对象
     */
    @GetMapping("/historyOrders")
    @ApiOperation("历史订单分页查询")
    public Result<PageResult> getListPage(int page, int pageSize, Integer status) {
        OrdersPageQueryDTO ordersPageQueryDTO = new OrdersPageQueryDTO();
        ordersPageQueryDTO.setPage(page);
        ordersPageQueryDTO.setPageSize(pageSize);
        ordersPageQueryDTO.setStatus(status);

        PageResult pageResult = orderService.getListPage(ordersPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 查询订单详情
     *
     * @param id 订单id
     * @return Result类响应对象
     */
    @ApiOperation("查询订单详情")
    @GetMapping("/orderDetail/{id}")
    public Result<OrderVO> details(@PathVariable Long id) {
        OrderVO orderVO = orderService.details(id);
        return Result.success(orderVO);
    }

    /**
     * 取消订单
     *
     * @param id 订单id
     * @return Result类响应对象
     */
    @PutMapping("/cancel/{id}")
    @ApiOperation("取消订单")
    public Result cancel(@PathVariable Long id) throws Exception {
        orderService.cancel(id);
        return Result.success();
    }

    /**
     * 再来一单
     *
     * @param id 订单id
     * @return Result类响应对象
     */
    @PostMapping("/repetition/{id}")
    @ApiOperation("再来一单")
    public Result repetition(@PathVariable Long id) {
        orderService.repetition(id);
        return Result.success();
    }
}
