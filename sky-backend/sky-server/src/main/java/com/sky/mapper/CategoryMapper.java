package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

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
    @AutoFill(OperationType.INSERT)
    Integer addCategory(Category category);

    /**
     * 分类分页查询
     *
     * @param categoryPageQueryDTO 分类分页查询DTO
     * @return Page类分页对象
     */
    Page<Category> getCategoryListPage(CategoryPageQueryDTO categoryPageQueryDTO);

    /**
     * 根据id删除分类
     *
     * @param id 分类id
     */
    @Delete("delete from category where id = #{id}")
    void deleteCategoryById(Long id);

    /**
     * 根据id修改分类
     *
     * @param category 分类对象
     */
    @AutoFill(OperationType.UPDATE)
    void updateCategory(Category category);

    /**
     * 根据类型查询分类
     *
     * @param type 类型分类
     * @return List<Category>分类集合类
     */
    List<Category> getCategoryListByType(Integer type);


    /**
     * 根据id查询类型名字
     * @param id 类型id
     * @return String类类型名字
     * */
    @Select("select name from category where id = #{id}")
    String getCategoryNameById(Long id);
}
