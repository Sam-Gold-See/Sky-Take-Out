package com.sky.mapper;

import com.sky.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

@Mapper
public interface UserMapper {

    /**
     * 根据OpenId查询对象
     *
     * @param openId 微信ID
     * @return User对象实体类
     */
    @Select("select * from user where openid = #{openid}")
    User getByOpenId(String openId);

    /**
     * 存储新用户数据
     *
     * @param user 用户实体类对象
     */
    void insertUser(User user);

    /**
     * 动态查询用户数额
     */
    Integer countByMap(Map map);
}
