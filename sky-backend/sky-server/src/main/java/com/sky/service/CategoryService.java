package com.sky.service;

import com.sky.dto.CategoryDTO;

public interface CategoryService {

    /**
     * 新增分类
     *
     * @param categoryDTO 分类DTO对象
     * @return Integer类状态码
     */
    Integer addCategory(CategoryDTO categoryDTO);
}
