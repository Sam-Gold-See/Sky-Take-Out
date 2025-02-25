package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface DishMapper {

    /**
     * 根据分类id查询菜品数
     *
     * @param categoryId 分类id
     */
    @Select("select count(id) from dish where category_id = #{categoryId} ")
    Integer getCountByCategoryId(Long categoryId);

    /**
     * 插入菜品数据
     *
     * @param dish 菜品对象
     */
    @AutoFill(OperationType.INSERT)
    void insertDish(Dish dish);
}
