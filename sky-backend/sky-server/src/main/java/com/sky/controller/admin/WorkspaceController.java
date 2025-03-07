package com.sky.controller.admin;

import com.sky.result.Result;
import com.sky.service.WorkspaceService;
import com.sky.vo.BusinessDataVO;
import com.sky.vo.SetmealOverViewVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/admin/workspace")
@RestController
@Slf4j
@Api(tags = "工作台相关接口")
public class WorkspaceController {

    @Autowired
    private WorkspaceService workspaceService;

    /**
     * 查询今日运营数据
     *
     * @return Result类响应数据
     */
    @GetMapping("/businessData")
    @ApiOperation("查询今日运营数据")
    public Result<BusinessDataVO> getBusinessData() {
        BusinessDataVO businessDataVO = workspaceService.getBusinessData();
        return Result.success(businessDataVO);
    }

    /**
     * 查询套餐总览
     *
     * @return Result类响应对象
     */
    @GetMapping("/overviewSetmeals")
    @ApiOperation("查询套餐总览")
    public Result<SetmealOverViewVO> getSetmealOverView() {
        return Result.success(workspaceService.getSetmealOverView());
    }
}
