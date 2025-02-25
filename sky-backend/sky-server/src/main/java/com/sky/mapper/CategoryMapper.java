package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryMapper {

    /**
     * 新增分类
     *
     * @param category 分类对象
     * @return Integer影响行数
     */
    @Insert("insert into category (type, name, sort, status, create_time, update_time, create_user, update_user) " +
            "values " +
            "(#{type}, #{name}, #{sort}, #{status}, #{createTime}, #{updateTime}, #{createUser}, #{updateUser})")
    Integer addCategory(Category category);

    /**
     * 分类分页查询
     *
     * @param categoryPageQueryDTO 分类分页查询DTO
     * @return Page类分页对象
     */
    Page<Category> getCategoryListPage(CategoryPageQueryDTO categoryPageQueryDTO);
}
