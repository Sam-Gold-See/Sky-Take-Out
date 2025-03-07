package com.sky.service;

import com.sky.vo.OrderReportVO;
import com.sky.vo.SalesTop10ReportVO;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;

public interface ReportService {

    /**
     * 营业额统计
     *
     * @param begin 开始日期
     * @param end   结束日期
     * @return TurnoverReportVO营业额报表VO对象
     */
    TurnoverReportVO getTurnoverStatistics(LocalDate begin, LocalDate end);

    /**
     * 用户数统计
     *
     * @param begin 开始日期
     * @param end   结束日期
     * @return UserReportVO用户报表VO对象
     */
    UserReportVO getUserStatistics(LocalDate begin, LocalDate end);

    /**
     * 订单数统计
     *
     * @param begin 开始日期
     * @param end   结束日期
     * @return OrderReportVO订单报表VO对象
     */
    OrderReportVO getOrderStatistics(LocalDate begin, LocalDate end);

    /**
     * 销量排行top10
     *
     * @param begin 开始日期
     * @param end   结束日期
     * @return SalesTop10ReportVO销量排行前十报表VO对象
     */
    SalesTop10ReportVO getSalesTop10Statistics(LocalDate begin, LocalDate end);

    /**
     * 导出运营数据报表
     *
     * @param response response
     */
    void exportBusinessData(HttpServletResponse response);
}
