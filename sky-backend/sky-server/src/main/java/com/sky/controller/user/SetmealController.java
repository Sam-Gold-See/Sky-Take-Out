package com.sky.controller.user;

import com.sky.constant.StatusConstant;
import com.sky.entity.Setmeal;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.DishItemVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController("userSetmealController")
@Api("套餐相关接口")
@RequestMapping("/user/setmeal")
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    /**
     * 根据分类id查询套餐
     *
     * @param categoryId 分类id
     * @return Result类响应对象
     */
    @ApiOperation("根据分类id查询套餐")
    @GetMapping("/list")
    @Cacheable(cacheNames = "setmealCache", key = "#categoryId")
    public Result<List<Setmeal>> getSetmealByCategoryId(Long categoryId) {
        Setmeal setmeal = Setmeal.builder()
                .categoryId(categoryId)
                .status(StatusConstant.ENABLE)
                .build();

        List<Setmeal> list = setmealService.getSetmealByCategoryId(setmeal);
        return Result.success(list);
    }

    /**
     * 根据套餐id查询包含的菜品列表
     *
     * @param id 套餐id
     * @return Result类响应对象
     */
    @ApiOperation("根据套餐id查询包含的菜品列表")
    @GetMapping("/dish/{id}")
    public Result<List<DishItemVO>> getDishItemBySetmealId(@PathVariable Long id) {
        List<DishItemVO> list = setmealService.getDishItemBySetmealId(id);
        return Result.success(list);
    }
}
