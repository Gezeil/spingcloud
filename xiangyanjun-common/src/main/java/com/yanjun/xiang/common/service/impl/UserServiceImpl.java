package com.yanjun.xiang.common.service.impl;

import com.yanjun.xiang.common.dao.UserMapper;
import com.yanjun.xiang.common.entity.User;
import com.yanjun.xiang.common.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void userRegister(User user) {
        userMapper.userRegister(user);
    }

    @Override
    public String selectAsaltByName(String name) {
        return null;
    }
}
