package com.sky.controller.user;

import com.sky.entity.Category;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("userCategoryController")
@Slf4j
@Api(tags = "C端分类相关接口")
@RequestMapping("/user/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 获取分类列表
     *
     * @param type 查询分类类型：1 菜品分类 2 套餐分类
     * @return Result类响应对象
     */
    @ApiOperation("获取分类列表")
    @GetMapping("/list")
    public Result<List<Category>> getCategoryListPage(Integer type) {
        List<Category> list = categoryService.getCategoryListByType(type);
        return Result.success(list);
    }
}
