package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface SetmealMapper {

    /**
     * 根据分类id查询套餐数
     *
     * @param categoryId 分类id
     */
    @Select("select count(id) from setmeal where category_id = #{categoryId}")
    Integer getCountByCategoryId(Long categoryId);

    /**
     * 根据id查询套餐VO类
     *
     * @param id 套餐id
     * @return SetmealVO套餐VO类对象
     */
    @Select("select * from setmeal where id = #{id}")
    SetmealVO getSetmealVOById(Long id);

    /**
     * 新增套餐
     *
     * @param setmeal 套餐实体类对象
     */
    @Insert("insert into setmeal (category_id, name, price, description, image, create_time, update_time, create_user, update_user)" +
            " values " +
            "(#{categoryId}, #{name}, #{price}, #{description}, #{image}, #{createTime}, #{updateTime}, #{createUser}, #{updateUser})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @AutoFill(OperationType.INSERT)
    void insertSetmeal(Setmeal setmeal);

    /**
     * 套餐分页查询
     *
     * @param setmealPageQueryDTO 套餐分页查询DTO类对象
     * @return Page<SetmealVO>类分页集合类
     */
    Page<SetmealVO> getSetmealListPage(SetmealPageQueryDTO setmealPageQueryDTO);

    /**
     * 根据id动态更新套餐
     *
     * @param setmeal 套餐实体类对象
     */
    @AutoFill(OperationType.UPDATE)
    void updateSetmeal(Setmeal setmeal);

    /**
     * 根据id查询套餐
     *
     * @param id 套餐id
     * @return Setmeal实体类对象
     */
    @Select("select * from setmeal where id = #{id}")
    Setmeal getSetmealById(Long id);

    /**
     * 根据id删除套餐
     *
     * @param id 套餐id
     */
    @Delete("delete from setmeal where id = #{id}")
    void deleteSetmealById(Long id);

    /**
     * 根据分类id查询套餐
     *
     * @param setmeal 套餐实体类对象（仅包含分类id和在售状态）
     * @return List<Setmeal>套餐集合类
     */
    @Select("select * from setmeal where category_id = #{categoryId} and status = #{status}")
    List<Setmeal> getSetmealByCategoryId(Setmeal setmeal);

    /**
     * 根据套餐id查询包含的菜品列表
     *
     * @param setmealId 套餐id
     * @return List<DishItemVO>
     */
    @Select("select sd.name, sd.copies, d.image, d.description " +
            "from setmeal_dish sd left join dish d on sd.dish_id = d.id " +
            "where sd.setmeal_id = #{setmealId}")
    List<DishItemVO> getDishItemBySetmealId(Long setmealId);

    /**
     * 根据参数map动态查询套餐个数列表
     *
     * @param map 参数列表
     */
    Integer countByMap(Map map);
}
