package com.sky.controller.admin;

import com.sky.result.Result;
import com.sky.service.ReportService;
import com.sky.vo.OrderReportVO;
import com.sky.vo.SalesTop10ReportVO;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@Slf4j
@Api(tags = "数据统计相关接口")
@RequestMapping("/admin/report")
public class ReportController {

    @Autowired
    ReportService reportService;

    /**
     * 营业额统计
     *
     * @param begin 开始日期
     * @param end   结束日期
     * @return Result类响应对象
     */
    @ApiOperation("营业额统计")
    @GetMapping("/turnoverStatistics")
    public Result<TurnoverReportVO> turnoverStatistics(
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {
        return Result.success(reportService.getTurnoverStatistics(begin, end));
    }

    /**
     * 用户数统计
     *
     * @param begin 开始日期
     * @param end   结束日期
     * @return Result类响应对象
     */
    @GetMapping("/userStatistics")
    @ApiOperation("用户数统计")
    public Result<UserReportVO> userStatistics(
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {
        return Result.success(reportService.getUserStatistics(begin, end));
    }

    /**
     * 订单数统计
     *
     * @param begin 开始日期
     * @param end   结束日期
     * @return Result类响应对象
     */
    @GetMapping("/ordersStatistics")
    @ApiOperation("订单数统计")
    public Result<OrderReportVO> ordersStatistics(
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {
        return Result.success(reportService.getOrderStatistics(begin, end));
    }

    /**
     * 销量排行top10
     *
     * @param begin 开始日期
     * @param end   结束日期
     * @return Result类响应对象
     */
    @GetMapping("/top10")
    @ApiOperation("销量排行top10")
    public Result<SalesTop10ReportVO> salesTop10Statistics(
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {
        return Result.success(reportService.getSalesTop10Statistics(begin, end));
    }
}
