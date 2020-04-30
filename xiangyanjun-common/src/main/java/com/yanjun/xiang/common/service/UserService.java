package com.yanjun.xiang.common.service;

import com.yanjun.xiang.common.entity.User;

public interface UserService {
    void userRegister(User user);

    String selectAsaltByName(String name);
}
