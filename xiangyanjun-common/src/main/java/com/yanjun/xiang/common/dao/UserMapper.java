package com.yanjun.xiang.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yanjun.xiang.common.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface UserMapper extends BaseMapper<User> {

    Integer userRegister(@Param("user") User user);

    User selectUserInfo(@Param("name") String name);
}
