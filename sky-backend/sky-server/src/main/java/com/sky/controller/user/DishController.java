package com.sky.controller.user;

import com.sky.constant.StatusConstant;
import com.sky.entity.Dish;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api("菜品相关接口")
@Slf4j
@RestController("userDishController")
@RequestMapping("/user/dish")
public class DishController {

    @Autowired
    DishService dishService;

    /**
     * 根据分类id查询菜品
     *
     * @param categoryId 分类id
     * @return Result类响应对象
     */
    @ApiOperation("根据分类id查询菜品")
    @GetMapping("/list")
    public Result<List<DishVO>> getDishVOListByCategoryId(Long categoryId) {
        Dish dish = Dish.builder()
                .categoryId(categoryId)
                .status(StatusConstant.ENABLE)
                .build();

        List<DishVO> list = dishService.getDishVOListByCategoryId(dish);

        return Result.success(list);
    }
}
