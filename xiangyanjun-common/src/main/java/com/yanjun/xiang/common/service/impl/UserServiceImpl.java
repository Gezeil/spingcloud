package com.yanjun.xiang.common.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yanjun.xiang.common.dao.UserMapper;
import com.yanjun.xiang.common.entity.User;
import com.yanjun.xiang.common.service.UserService;
import com.yanjun.xiang.common.util.JwtUtils;
import com.yanjun.xiang.common.util.UserRegisteAndLogin;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;


@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private StringRedisTemplate redisTemplate;

    public final String key = "token";

    /**
     * 登录
     * @param user
     * @return
     */
    @Override
    public String userLogin(User user) {
        User userInfo = userMapper.selectUserInfo(user.getName());
        user.setPassword(UserRegisteAndLogin.getInputPasswordCiph(user.getPassword(), userInfo.getSalt()));
        UserRegisteAndLogin.userLogin(user);
        String token = token(userInfo);
        return token;
    }

    public String token(User userInfo){
        String token = JwtUtils.sign(userInfo.getName(), userInfo.getId());
        redisTemplate.opsForValue().set(token,JSON.toJSONString(userInfo));
        return token;
    }

    /**
     * 注册
     * @param user
     * @return
     */
    @Override
    public String userRegister(User user) {
        userMapper.insert(user);
        return token(user);
    }
}
