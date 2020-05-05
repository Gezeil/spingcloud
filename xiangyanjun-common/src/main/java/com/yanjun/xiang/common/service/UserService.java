package com.yanjun.xiang.common.service;

import com.yanjun.xiang.common.entity.User;

public interface UserService {
    String userRegister(User user);

    String userLogin(User user);
}
