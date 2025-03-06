package com.sky.service;

import com.sky.vo.TurnoverReportVO;

import java.time.LocalDate;

public interface ReportService {

    /**
     * 营业额统计
     *
     * @param begin 开始日期
     * @param end   结束日期
     * @return TurnoverReportVO报表VO对象
     */
    TurnoverReportVO getTurnoverStatistics(LocalDate begin, LocalDate end);
}
