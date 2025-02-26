package com.sky.controller.admin;

import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/setmeal")
@Slf4j
@Api("套餐相关接口")
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    /**
     * 根据id查询套餐
     *
     * @param id 套餐id
     * @return SetmealVO套餐VO类对象
     */
    @GetMapping("/{id}")
    @ApiOperation("根据id查询套餐")
    public Result<SetmealVO> getSetmealVOById(@PathVariable Long id) {
        SetmealVO setmealVO = setmealService.getSetmealVOById(id);
        return Result.success(setmealVO);
    }
}
