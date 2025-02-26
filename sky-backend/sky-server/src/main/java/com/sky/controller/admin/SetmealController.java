package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 新增套餐
     *
     * @param setmealDTO 套餐DTO类对象
     * @return Result类响应对象
     */
    @PostMapping
    @ApiOperation("新增套餐")
    public Result insertSetmeal(@RequestBody SetmealDTO setmealDTO) {
        setmealService.insertSetmeal(setmealDTO);
        return Result.success();
    }
}
