package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * 菜品管理
 */
@RestController("adminDishController")
@RequestMapping("/admin/dish")
@Api(tags = "菜品相关接口")
@Slf4j
public class DishController {

    @Autowired
    RedisTemplate redisTemplate;

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

        //清理缓存数据
        String key = "dish_" + dishDTO.getCategoryId();
        cleanCache(key);

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

        //将所有菜品缓存数据清理掉：所有以dish_开头的key
        cleanCache("dish_*");

        return Result.success();
    }

    /**
     * 根据id查询菜品和口味数据
     *
     * @param id 菜品id
     * @return Result类响应对象
     */
    @GetMapping("/{id}")
    @ApiOperation("根据id查询菜品")
    public Result<DishVO> getDishVOById(@PathVariable Long id) {
        DishVO dishVO = dishService.getDishVOById(id);
        return Result.success(dishVO);
    }

    /**
     * 启用禁用菜品
     *
     * @param status 目标启用/禁用状态信息
     * @param id     菜品id
     */
    @PostMapping("/status/{status}")
    @ApiOperation("启用禁用菜品")
    public Result startOrStopDish(@PathVariable Integer status, Long id) {
        dishService.startOrStopDish(status, id);

        //将所有菜品缓存数据清理掉：所有以dish_开头的key
        cleanCache("dish_*");

        return Result.success();
    }

    /**
     * 修改菜品
     *
     * @param dishDTO 菜品DTO对象类
     * @return Result对象类
     */
    @PutMapping
    @ApiOperation("修改菜品")
    public Result updateDishById(@RequestBody DishDTO dishDTO) {
        dishService.updateDishWithFlavorById(dishDTO);

        //将所有菜品缓存数据清理掉：所有以dish_开头的key
        cleanCache("dish_*");

        return Result.success();
    }

    /**
     * 根据分类id查询菜品
     *
     * @param categoryId 分类id
     * @return Result类响应对象
     */
    @GetMapping("/list")
    @ApiOperation("根据分类id查询菜品")
    public Result<List<Dish>> getDishListByCategoryId(Long categoryId) {
        List<Dish> list = dishService.getDishListByCategoryId(categoryId);
        return Result.success(list);
    }

    /**
     * 清理缓存数据
     *
     * @param pattern 匹配模式
     */
    private void cleanCache(String pattern) {
        Set keys = redisTemplate.keys(pattern);
        redisTemplate.delete(keys);
    }
}
