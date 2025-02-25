package com.sky.service.impl;

import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.CategoryDTO;
import com.sky.entity.Category;
import com.sky.mapper.CategoryMapper;
import com.sky.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 新增分类
     *
     * @param categoryDTO 分类DTO对象
     * @return Integer类状态码
     */
    @Override
    public Integer addCategory(CategoryDTO categoryDTO) {
        Category category = new Category();

        BeanUtils.copyProperties(categoryDTO, category);

        category.setStatus(StatusConstant.DISABLE);

        category.setCreateTime(LocalDateTime.now());

        category.setUpdateTime(LocalDateTime.now());

        category.setCreateUser(BaseContext.getCurrentId());
        category.setUpdateUser(BaseContext.getCurrentId());

        return categoryMapper.addCategory(category);
    }
}
