package com.sky.controller.admin;

import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * 分类管理
 */
@RestController
@RequestMapping("/admin/category")
@Slf4j
@Api("员工相关接口")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 新增分类
     *
     * @param categoryDTO 分类DTO对象
     * @return Result类响应对象
     */
    @PostMapping
    @ApiOperation("新增分类")
    public Result addCategory(@RequestBody CategoryDTO categoryDTO) {
        Integer status = categoryService.addCategory(categoryDTO);
        Result result;
        if (Objects.equals(status, StatusConstant.ENABLE))
            result = Result.success();
        else
            result = Result.error(MessageConstant.UNKNOWN_ERROR);
        return result;
    }

    /**
     * 分类分页查询
     *
     * @param categoryPageQueryDTO 分类分页查询DTO
     * @return Result类响应对象
     */
    @GetMapping("/page")
    @ApiOperation("分类分页查询")
    public Result<PageResult> getCategoryListPage(CategoryPageQueryDTO categoryPageQueryDTO) {
        PageResult pageResult = categoryService.getCategoryListPage(categoryPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 根据id删除分类
     *
     * @param id 分类id
     * @return Result类响应对象
     */
    @DeleteMapping
    @ApiOperation("根据id删除分类")
    public Result deleteCategoryById(@RequestParam Long id) {
        categoryService.deleteCategoryById(id);
        return Result.success();
    }

    /**
     * 根据id修改分类
     *
     * @param categoryDTO 分类DTO对象
     * @return Result类响应对象
     */
    @PutMapping
    @ApiOperation("根据id修改分类")
    public Result updateCategoryById(@RequestBody CategoryDTO categoryDTO) {
        categoryService.updateCategoryById(categoryDTO);
        return Result.success();
    }

    /**
     * 启用禁用分类
     *
     * @param status 目标启用/禁用状态信息
     * @param id     分类id
     * @return Result类响应对象
     */
    @PostMapping("/status/{status}")
    @ApiOperation("启用禁用分类")
    public Result startOrStopCategory(@PathVariable Integer status, @RequestParam Long id) {
        categoryService.startOrStopCategory(status, id);
        return Result.success();
    }
}
