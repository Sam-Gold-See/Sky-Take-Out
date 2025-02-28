package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.CategoryMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private SetmealMapper setmealMapper;

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

        return categoryMapper.addCategory(category);
    }

    /**
     * 分类分页查询
     *
     * @param categoryPageQueryDTO 分类分页查询DTO
     * @return PageResult类分页查询响应对象
     */
    @Override
    public PageResult getCategoryListPage(CategoryPageQueryDTO categoryPageQueryDTO) {
        PageHelper.startPage(categoryPageQueryDTO.getPage(), categoryPageQueryDTO.getPageSize());

        Page<Category> page = categoryMapper.getCategoryListPage(categoryPageQueryDTO);

        long total = page.getTotal();
        List<Category> records = page.getResult();

        return new PageResult(total, records);
    }

    /**
     * 根据id删除分类
     *
     * @param id 分类id
     */
    @Override
    public void deleteCategoryById(Long id) {
        //查询当前分类是否关联了菜品，如果关联了就抛出业务异常
        Integer count = dishMapper.getCountByCategoryId(id);
        if (count > 0)
            //当前分类下有菜品，不能删除
            throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_DISH);

        count = setmealMapper.getCountByCategoryId(id);
        if (count > 0)
            //当前分类下有套餐
            throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_SETMEAL);

        //删除分类数据
        categoryMapper.deleteCategoryById(id);
    }

    /**
     * 根据id修改分类
     *
     * @param categoryDTO 分类DTO对象
     */
    @Override
    public void updateCategoryById(CategoryDTO categoryDTO) {
        Category category = new Category();

        BeanUtils.copyProperties(categoryDTO, category);

        categoryMapper.updateCategory(category);
    }

    /**
     * 启用禁用分类
     *
     * @param status 目标启用/禁用状态信息
     * @param id     分类id
     */
    @Override
    public void startOrStopCategory(Integer status, Long id) {
        Category category = new Category();

        category.setStatus(status);
        category.setId(id);

        categoryMapper.updateCategory(category);
    }

    /**
     * 根据类型查询分类
     *
     * @param type 类型分类
     * @return List<Category>分类集合类
     */
    @Override
    public List<Category> getCategoryListByType(Integer type) {
        return categoryMapper.getCategoryListByType(type);
    }
}
