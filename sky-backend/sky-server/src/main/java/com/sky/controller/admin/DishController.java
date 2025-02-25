package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 菜品管理
 */
@RestController
@RequestMapping("/admin/dish")
@Api(tags = "菜品相关接口")
@Slf4j
public class DishController {


    @Autowired
    DishService dishService;

    /**
     * 新增菜品
     *
     * @param dishDTO 菜品DTO对象
     * @return Result类响应对象
     */
    @PostMapping
    @ApiOperation("新增菜品")
    public Result addDish(@RequestBody DishDTO dishDTO) {
        dishService.addDishWithFlavors(dishDTO);
        return Result.success();
    }

    /**
     * 菜品分页查询
     *
     * @param dishPageQueryDTO 菜品分页查询DTO
     * @return Result类响应对象
     */
    @GetMapping("/page")
    @ApiOperation("菜品分页查询")
    public Result<PageResult> pageDishListPage(DishPageQueryDTO dishPageQueryDTO) {
        PageResult pageResult = dishService.getDishListPage(dishPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 菜品的批量删除
     *
     * @param ids 菜品id集合
     * @return Result类响应对象
     */
    @DeleteMapping
    @ApiOperation("菜品批量删除")
    public Result deleteDishes(@RequestParam List<Long> ids) {
        dishService.deleteDishes(ids);
        return Result.success();
    }
}
