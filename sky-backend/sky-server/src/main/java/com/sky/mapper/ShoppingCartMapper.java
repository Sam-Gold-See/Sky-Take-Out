package com.sky.mapper;

import com.sky.entity.ShoppingCart;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ShoppingCartMapper {

    /**
     * 动态查询购物车
     *
     * @param shoppingCart 购物车实体对象
     * @return List<ShoppingCart>购物车集合类对象
     */
    List<ShoppingCart> listShoppingCart(ShoppingCart shoppingCart);

    /**
     * 根据id修改商品
     *
     * @param shoppingCart 购物车实体对象
     */
    @Update("update shopping_cart set number = #{number} where id = #{id}")
    void updateNumberById(ShoppingCart shoppingCart);

    /**
     * 插入购物车数据
     *
     * @param shoppingCart 购物车实体对象
     */
    @Insert("insert into shopping_cart (name, image, user_id, dish_id, setmeal_id, dish_flavor, amount, create_time) " +
            "values(#{name}, #{image}, #{userId}, #{dishId}, #{setmealId}, #{dishFlavor}, #{amount}, #{createTime})")
    void insertShoppingCart(ShoppingCart shoppingCart);

    /**
     * 动态删除购物车数据
     *
     * @param shoppingCart 购物车实体对象
     */
    void deleteShoppingCart(ShoppingCart shoppingCart);
}
