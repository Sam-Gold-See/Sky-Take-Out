package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishFlavorMapper {

    /**
     * 批量插入口味数据
     *
     * @param flavors 口味集合类
     */
    void insertFlavorsBatch(List<DishFlavor> flavors);

    /**
     * 根据菜品id删除口味
     *
     * @param dishId 菜品id
     */
    @Delete("delete from dish_flavor where dish_id = #{dishId}")
    void deleteFlavorByDishId(Long dishId);

    /**
     * 根据菜品id集合删除口味
     *
     * @param dishIds 菜品id集合
     */
    void deleteFlavorByDishIds(List<Long> dishIds);

    /**
     * 根据id查询菜品口味
     *
     * @param dishId 菜品id
     * @return List<DishFlavor>菜品VO对象集合类
     */
    @Select("select * from dish_flavor where dish_id = #{dishId}")
    List<DishFlavor> getFlavorsByDishId(Long dishId);
}
