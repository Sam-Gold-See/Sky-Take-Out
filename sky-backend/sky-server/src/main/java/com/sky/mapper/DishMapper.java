package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

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

    /**
     * 菜品分页查询
     *
     * @param dishPageQueryDTO 菜品分页查询
     * @return Page类分页对象
     */
    Page<DishVO> getDishListPage(DishPageQueryDTO dishPageQueryDTO);

    /**
     * 根据id查询菜品
     *
     * @param id 菜品id
     * @return Dish菜品类对象
     */
    @Select("select * from dish where id = #{id}")
    Dish getDishById(Long id);

    /**
     * 根据id删除菜品
     *
     * @param id 菜品id
     */
    @Delete("delete from dish where id = #{id}")
    void deleteDishById(Long id);

    /**
     * 根据菜品id集合删除菜品
     *
     * @param ids 菜品id集合
     */
    void deleteDishByIds(List<Long> ids);

    /**
     * 根据主键id动态修改属性
     *
     * @param dish 菜品实体类对象
     */
    @AutoFill(OperationType.UPDATE)
    void updateDish(Dish dish);

    /**
     * 根据分类id查询菜品
     *
     * @param categoryId 分类id
     * @return List<Dish>菜品集合类
     */
    @Select("select * from dish where category_id = #{categoryId}")
    List<Dish> getDishListByCategoryId(Long categoryId);

    /**
     * 根据套餐id查询菜品集合
     *
     * @param setmealId 套餐id
     */
    @Select("select d.* from dish d left join setmeal_dish sd " +
            "on d.id = sd.dish_id " +
            "where setmeal_id = #{setmealId}")
    List<Dish> getDishListBySetmealId(Long setmealId);
}
