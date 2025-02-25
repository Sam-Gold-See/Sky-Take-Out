package com.sky.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SetmealMapper {

    /**
     * 根据分类id查询套餐数
     *
     * @param categoryId 分类id
     */
    @Select("select count(id) from setmeal where category_id = #{categoryId}")
    Integer getCountByCategoryId(Long categoryId);
}
