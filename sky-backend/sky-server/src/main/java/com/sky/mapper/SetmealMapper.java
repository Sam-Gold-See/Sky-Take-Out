package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.enumeration.OperationType;
import com.sky.vo.SetmealVO;
import org.apache.ibatis.annotations.*;

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
}
