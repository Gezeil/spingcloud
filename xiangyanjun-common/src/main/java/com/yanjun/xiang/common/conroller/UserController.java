package com.yanjun.xiang.common.conroller;

import com.yanjun.xiang.common.annotation.PostApi;
import com.yanjun.xiang.common.dto.UserRegisterDTO;
import com.yanjun.xiang.common.entity.User;
import com.yanjun.xiang.common.util.UserRegisteAndLogin;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
public class UserController {

//    @Autowired
//    private UserService service;

    /**
     * 处理用户的登录请求
     */
    @PostApi("/userLogin")
    @ApiOperation(value = "登录")
    public boolean userLogin(@RequestBody User user)
    {
        user.setPassword(UserRegisteAndLogin.getInputPasswordCiph(user.getPassword(), service.selectAsaltByName(user.getName())));

        return UserRegisteAndLogin.userLogin(user);
    }

    @PostApi(value = "/userRegister")
    @ApiOperation(value = "注册")
    public boolean userRegister(@RequestBody User user){
        String password = user.getPassword();

        String[] saltAndCiphertext = UserRegisteAndLogin.encryptPassword(password);

        user.setSalt(saltAndCiphertext[0]);
        user.setPassword(saltAndCiphertext[1]);

        //存
//        service.userRegister(user);

        return UserRegisteAndLogin.userLogin(user); //使用户沆注册后立马登录
    }
}

