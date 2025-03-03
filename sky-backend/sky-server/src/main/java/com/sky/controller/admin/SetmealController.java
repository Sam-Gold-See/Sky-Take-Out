package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("adminSetmealController")
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

    /**
     * 套餐分页查询
     *
     * @param setmealPageQueryDTO 套餐分页查询DTO类对象
     * @return Result类响应对象
     */
    @GetMapping("/page")
    @ApiOperation("套餐分页查询")
    public Result<PageResult> getSetmealListPage(SetmealPageQueryDTO setmealPageQueryDTO) {
        PageResult pageResult = setmealService.getSetmealListPage(setmealPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 起售/停售套餐
     *
     * @param status 目标套餐起售停售状态
     * @param id     目标套餐id
     * @return Result类响应对象
     */
    @PostMapping("/status/{status}")
    @ApiOperation("起售/停售套餐")
    public Result startOrStopSetmeal(@PathVariable Integer status, Long id) {
        setmealService.startOrStopSetmeal(status, id);
        return Result.success();
    }

    /**
     * 根据id批量删除套餐
     *
     * @param ids 套餐id集合类
     * @return Result类响应对象
     */
    @DeleteMapping
    @ApiOperation("批量删除套餐")
    public Result deleteSetmealById(@RequestParam List<Long> ids) {
        setmealService.deleteSetmealByIds(ids);
        return Result.success();
    }

    /**
     * 修改套餐
     *
     * @param setmealDTO 套餐DTO类对象
     * @return Result类响应对象
     */
    @PutMapping
    @ApiOperation("修改套餐")
    public Result updateSetmeal(@RequestBody SetmealDTO setmealDTO) {
        setmealService.updateSetmeal(setmealDTO);
        return Result.success();
    }
}
