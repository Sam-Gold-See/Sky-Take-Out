package com.sky.service;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.result.PageResult;

public interface CategoryService {

    /**
     * 新增分类
     *
     * @param categoryDTO 分类DTO对象
     * @return Integer类状态码
     */
    Integer addCategory(CategoryDTO categoryDTO);

    /**
     * 分类分页查询
     *
     * @param categoryPageQueryDTO 分类分页查询DTO对象
     * @return PageResult
     */
    PageResult getCategoryListPage(CategoryPageQueryDTO categoryPageQueryDTO);

    /**
     * 根据id删除分类
     *
     * @param id 分类id
     */
    void deleteCategoryById(Long id);

    /**
     * 根据id修改分类
     *
     * @param categoryDTO 分类DTO对象
     */
    void updateCategoryById(CategoryDTO categoryDTO);

    /**
     * 启用禁用分类
     *
     * @param status 目标启用/禁用状态信息
     * @param id     分类id
     */
    void startOrStopCategory(Integer status, Long id);
}
