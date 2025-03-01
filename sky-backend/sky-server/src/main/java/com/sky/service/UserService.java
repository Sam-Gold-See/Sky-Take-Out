package com.sky.service;

import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;

public interface UserService {

    /**
     * 微信登录
     *
     * @param userLoginDTO 用户登录DTO类对象
     * @return User 用户实体类对象
     */
    User login(UserLoginDTO userLoginDTO);
}
