package com.sky.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.context.BaseContext;
import com.sky.dto.*;
import com.sky.entity.*;
import com.sky.exception.AddressBookBusinessException;
import com.sky.exception.OrderBusinessException;
import com.sky.exception.ShoppingCartBusinessException;
import com.sky.mapper.*;
import com.sky.result.PageResult;
import com.sky.service.OrderService;
import com.sky.utils.WeChatPayUtil;
import com.sky.vo.OrderPaymentVO;
import com.sky.vo.OrderStatisticsVO;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    OrderDetailMapper orderDetailMapper;

    @Autowired
    ShoppingCartMapper shoppingCartMapper;

    @Autowired
    AddressBookMapper addressBookMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    WeChatPayUtil weChatPayUtil;

    /**
     * 用户下单
     *
     * @param ordersSubmitDTO 用户订单提交DTO对象
     * @return OrderSubmitVO 订单提交VO对象
     */
    @Transactional
    @Override
    public OrderSubmitVO submit(OrdersSubmitDTO ordersSubmitDTO) {

        //处理业务异常（地址簿为空，购物车数据为空）
        AddressBook addressBook = AddressBook.builder()
                .id(ordersSubmitDTO.getAddressBookId())
                .build();
        addressBook = addressBookMapper.list(addressBook).get(0);
        if (addressBook == null)
            throw new AddressBookBusinessException(MessageConstant.ADDRESS_BOOK_IS_NULL);

        Long userId = BaseContext.getCurrentId();
        ShoppingCart shoppingCart = ShoppingCart.builder()
                .userId(userId)
                .build();
        List<ShoppingCart> shoppingCartList = shoppingCartMapper.listShoppingCart(shoppingCart);

        if (shoppingCartList == null || shoppingCartList.isEmpty())
            throw new ShoppingCartBusinessException(MessageConstant.SHOPPING_CART_IS_NULL);

        //向订单表插入1条数据
        Orders orders = new Orders();
        BeanUtils.copyProperties(ordersSubmitDTO, orders);
        orders.setOrderTime(LocalDateTime.now());
        orders.setPayStatus(Orders.UN_PAID);
        orders.setStatus(Orders.PENDING_PAYMENT);
        orders.setNumber(String.valueOf(System.currentTimeMillis()));
        orders.setPhone(addressBook.getPhone());
        orders.setConsignee(addressBook.getConsignee());
        orders.setUserId(userId);

        orderMapper.insert(orders);

        List<OrderDetail> orderDetailList = new ArrayList<>();
        //向订单明细表插入n条数据
        for (ShoppingCart cart : shoppingCartList) {
            OrderDetail orderDetail = new OrderDetail();
            BeanUtils.copyProperties(cart, orderDetail);
            orderDetail.setOrderId(orders.getId());
            orderDetailList.add(orderDetail);
        }
        orderDetailMapper.insertBatch(orderDetailList);

        //清空当前用户的购物车数据
        shoppingCartMapper.deleteShoppingCart(ShoppingCart.builder().userId(userId).build());

        //封装VO返回结果
        return OrderSubmitVO.builder()
                .id(orders.getId())
                .orderTime(orders.getOrderTime())
                .orderNumber(orders.getNumber())
                .orderAmount(orders.getAmount())
                .build();
    }

    /**
     * 订单支付
     *
     * @param ordersPaymentDTO 订单支付DTO对象
     * @return OrderPaymentVO 订单支付VO对象
     */
    public OrderPaymentVO payment(OrdersPaymentDTO ordersPaymentDTO) throws Exception {
        // 当前登录用户id
        Long userId = BaseContext.getCurrentId();
        User user = userMapper.getByOpenId(String.valueOf(userId));

        //调用微信支付接口，生成预支付交易单
        JSONObject jsonObject = weChatPayUtil.pay(
                ordersPaymentDTO.getOrderNumber(), //商户订单号
                new BigDecimal("0.01"), //支付金额，单位 元
                "苍穹外卖订单", //商品描述
                user.getOpenid() //微信用户的openid
        );

        if (jsonObject.getString("code") != null && jsonObject.getString("code").equals("ORDERPAID")) {
            throw new OrderBusinessException("该订单已支付");
        }

        OrderPaymentVO vo = jsonObject.toJavaObject(OrderPaymentVO.class);
        vo.setPackageStr(jsonObject.getString("package"));

        return vo;
    }

    /**
     * 支付成功，修改订单状态
     *
     * @param outTradeNo 交易情况
     */
    public void paySuccess(String outTradeNo) {

        // 根据订单号查询订单
        Orders ordersDB = orderMapper.getByNumber(outTradeNo);

        // 根据订单id更新订单的状态、支付方式、支付状态、结账时间
        Orders orders = Orders.builder()
                .id(ordersDB.getId())
                .status(Orders.TO_BE_CONFIRMED)
                .payStatus(Orders.PAID)
                .checkoutTime(LocalDateTime.now())
                .build();

        orderMapper.update(orders);
    }

    /**
     * 历史订单分页查询
     *
     * @param ordersPageQueryDTO 订单分页查询DTO对象
     * @return PageResult类响应对象
     */
    @Override
    public PageResult getListPage(OrdersPageQueryDTO ordersPageQueryDTO) {
        PageHelper.startPage(ordersPageQueryDTO.getPage(), ordersPageQueryDTO.getPageSize());

        Long userId = BaseContext.getCurrentId();
        ordersPageQueryDTO.setUserId(userId);

        Page<Orders> page = orderMapper.getListPage(ordersPageQueryDTO);

        long total = page.getTotal();
        List<OrderVO> records = new ArrayList<>();

        if (total > 0)
            for (Orders orders : page) {
                Long orderId = orders.getId();

                List<OrderDetail> orderDetails =
                        orderDetailMapper.list(Orders.builder()
                                .id(orderId)
                                .build());

                OrderVO orderVO = new OrderVO();
                BeanUtils.copyProperties(orders, orderVO);
                orderVO.setOrderDetailList(orderDetails);

                records.add(orderVO);
            }
        return new PageResult(total, records);
    }

    /**
     * 查询订单详情
     *
     * @param id 订单id
     * @return OrderVO订单VO类对象
     */
    @Override
    public OrderVO details(Long id) {
        Orders searchOrders = Orders.builder().id(id).build();
        List<Orders> ordersList = orderMapper.list(searchOrders);
        Orders orders = ordersList.get(0);

        List<OrderDetail> orderDetailList = orderDetailMapper.list(searchOrders);

        OrderVO orderVO = new OrderVO();
        BeanUtils.copyProperties(orders, orderVO);
        orderVO.setOrderDetailList(orderDetailList);
        orderVO.setOrderDishes(getOrderDishesStr(searchOrders));

        return orderVO;
    }

    /**
     * 取消订单
     *
     * @param id 订单id
     */
    @Override
    public void cancel(Long id) throws Exception {
        List<Orders> ordersList = orderMapper.list(Orders.builder().id(id).build());
        if (ordersList == null || ordersList.isEmpty())
            throw new OrderBusinessException(MessageConstant.ORDER_NOT_FOUND);

        Orders canceledOrders = ordersList.get(0);
        if (canceledOrders.getStatus() > Orders.TO_BE_CONFIRMED)
            throw new OrderBusinessException(MessageConstant.ORDER_STATUS_ERROR);

        Orders orders = new Orders();
        orders.setId(canceledOrders.getId());

        if (canceledOrders.getStatus().equals(Orders.TO_BE_CONFIRMED)) {
            weChatPayUtil.refund(
                    canceledOrders.getNumber(),
                    canceledOrders.getNumber(),
                    new BigDecimal("0.01"),
                    new BigDecimal("0.01"));
            orders.setPayStatus(Orders.REFUND);
        }

        orders.setStatus(Orders.CANCELLED);
        orders.setCancelReason("用户取消");
        orders.setCancelTime(LocalDateTime.now());
        orderMapper.update(orders);
    }

    /**
     * 再来一单
     *
     * @param id 订单id
     */
    @Override
    public void repetition(Long id) {
        Long userId = BaseContext.getCurrentId();

        List<OrderDetail> orderDetailList = orderDetailMapper.list(Orders.builder().id(id).build());

        List<ShoppingCart> shoppingCartList = orderDetailList.stream().map(orderDetail -> {
            ShoppingCart shoppingCart = new ShoppingCart();

            BeanUtils.copyProperties(orderDetail, shoppingCart, "id");
            shoppingCart.setUserId(userId);
            shoppingCart.setCreateTime(LocalDateTime.now());

            return shoppingCart;
        }).collect(Collectors.toList());

        shoppingCartMapper.insertShoppingCartBatch(shoppingCartList);
    }

    /**
     * 订单条件搜索
     *
     * @param ordersPageQueryDTO 订单分页查询DTO对象
     * @return PageResult类响应对象
     */
    @Override
    public PageResult conditionSearch(OrdersPageQueryDTO ordersPageQueryDTO) {
        PageHelper.startPage(ordersPageQueryDTO.getPage(), ordersPageQueryDTO.getPageSize());

        Page<Orders> page = orderMapper.getListPage(ordersPageQueryDTO);

        List<OrderVO> records = getOrderVOList(page);

        return new PageResult(page.getTotal(), records);
    }

    /**
     * 各个状态的订单数统计
     *
     * @return OrderStatisticsVO订单统计数据VO对象
     */
    @Override
    public OrderStatisticsVO statistics() {
        Integer confirmed = orderMapper.count(Orders.CONFIRMED);
        Integer toBeConfirmed = orderMapper.count(Orders.TO_BE_CONFIRMED);
        Integer deliveryInProgress = orderMapper.count(Orders.DELIVERY_IN_PROGRESS);

        OrderStatisticsVO orderStatisticsVO = new OrderStatisticsVO();
        orderStatisticsVO.setConfirmed(confirmed);
        orderStatisticsVO.setToBeConfirmed(toBeConfirmed);
        orderStatisticsVO.setDeliveryInProgress(deliveryInProgress);

        return orderStatisticsVO;
    }

    /**
     * 接单
     *
     * @param id 订单id
     */
    @Override
    public void confirm(Long id) {
        Orders orders = Orders.builder()
                .id(id)
                .status(Orders.CONFIRMED)
                .build();

        orderMapper.update(orders);
    }

    /**
     * 拒单
     *
     * @param ordersRejectionDTO 拒单DTO对象
     */
    @Override
    public void rejection(OrdersRejectionDTO ordersRejectionDTO) {
        Long id = ordersRejectionDTO.getId();
        List<Orders> ordersList = orderMapper.list(Orders.builder().id(id).build());
        Orders ordersDB = ordersList.get(0);

        if (ordersDB == null || !ordersDB.getStatus().equals(Orders.TO_BE_CONFIRMED))
            throw new OrderBusinessException(MessageConstant.ORDER_STATUS_ERROR);

        Integer payStatus = ordersDB.getStatus();
        if (payStatus.equals(Orders.PAID)) {
            try {
                String refund = weChatPayUtil.refund(
                        ordersDB.getNumber(),
                        ordersDB.getNumber(),
                        new BigDecimal("0.01"),
                        new BigDecimal("0.01")
                );
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        Orders orders = Orders.builder()
                .id(id)
                .status(Orders.CANCELLED)
                .rejectionReason(ordersRejectionDTO.getRejectionReason())
                .cancelTime(LocalDateTime.now())
                .build();

        orderMapper.update(orders);
    }

    /**
     * 取消订单
     *
     * @param ordersCancelDTO 取消订单DTO对象
     */
    @Override
    public void cancel(OrdersCancelDTO ordersCancelDTO) {
        List<Orders> ordersList = orderMapper.list(Orders.builder().id(ordersCancelDTO.getId()).build());
        Orders ordersDB = ordersList.get(0);

        Integer payStatus = ordersDB.getStatus();
        if (payStatus.equals(Orders.PAID)) {
            try {
                String refund = weChatPayUtil.refund(
                        ordersDB.getNumber(),
                        ordersDB.getNumber(),
                        new BigDecimal("0.01"),
                        new BigDecimal("0.01")
                );
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        Orders orders = Orders.builder()
                .id(ordersCancelDTO.getId())
                .status(Orders.CANCELLED)
                .cancelReason(ordersCancelDTO.getCancelReason())
                .cancelTime(LocalDateTime.now())
                .build();

        orderMapper.update(orders);
    }

    /**
     * 派送订单
     *
     * @param id 订单id
     */
    @Override
    public void delivery(Long id) {
        List<Orders> ordersList = orderMapper.list(Orders.builder().id(id).build());
        Orders ordersDB = ordersList.get(0);

        if (ordersDB.getStatus().equals(Orders.CONFIRMED))
            throw new OrderBusinessException(MessageConstant.ORDER_STATUS_ERROR);

        Orders orders = Orders.builder()
                .id(id)
                .status(Orders.DELIVERY_IN_PROGRESS)
                .build();

        orderMapper.update(orders);
    }

    private List<OrderVO> getOrderVOList(Page<Orders> page) {
        List<OrderVO> orderVOList = new ArrayList<>();

        List<Orders> ordersList = page.getResult();
        if (!ordersList.isEmpty())
            for (Orders orders : ordersList) {
                OrderVO orderVO = new OrderVO();
                BeanUtils.copyProperties(orders, orderVO);
                String orderDishes = getOrderDishesStr(Orders.builder().id(orders.getId()).build());

                orderVO.setOrderDishes(orderDishes);
                orderVOList.add(orderVO);
            }
        return orderVOList;
    }

    private String getOrderDishesStr(Orders orders) {
        List<OrderDetail> orderDetailList = orderDetailMapper.list(orders);

        List<String> orderDishesList = orderDetailList.stream()
                .map(orderDetail -> orderDetail.getName() + "*" + orderDetail.getNumber() + ";")
                .collect(Collectors.toList());

        return String.join(",", orderDishesList);
    }
}
