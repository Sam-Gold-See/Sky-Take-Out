package com.sky.mapper;

import com.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SetmealDishMapper {

    /**
     * 根据菜品id查询套餐id
     *
     * @param dishIds 菜品id集合
     */
    List<Long> getSetmealDishIds(List<Long> dishIds);

    /**
     * 根据套餐id查询套餐内菜品信息
     *
     * @param setmealId 套餐id
     */
    List<SetmealDish> getSetmealDishListBySetmealId(Long setmealId);

    /**
     * 新增单个套餐内菜品关联
     *
     * @param setmealDish 套餐菜品关联类
     */
    @Insert("insert into setmeal_dish (setmeal_id, dish_id, name, price, copies) " +
            "values " +
            "(#{setmealId}, #{dishId}, #{name}, #{price}, #{copies})")
    void insertSetmealDish(SetmealDish setmealDish);

    /**
     * 新增多个套餐内菜品关联
     *
     * @param setmealDishes 套餐菜品关联集合类
     */
    void insertSetmealDishes(List<SetmealDish> setmealDishes);

    /**
     * 根据套餐id删除套餐菜品关联
     *
     * @param setmealId 套餐id
     */
    @Delete("delete from setmeal_dish where setmeal_id = #{setmealId}")
    void deleteSetmealDishBySetmealId(Long setmealId);
}
