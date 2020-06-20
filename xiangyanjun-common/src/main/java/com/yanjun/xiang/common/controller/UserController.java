package com.yanjun.xiang.common.controller;

import com.yanjun.xiang.common.annotation.GetApi;
import com.yanjun.xiang.common.annotation.PostApi;
import com.yanjun.xiang.common.entity.User;
import com.yanjun.xiang.common.mq.MQProducer;
import com.yanjun.xiang.common.service.UserService;
import com.yanjun.xiang.common.util.UserRegisteAndLogin;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
@Api(tags = "用户相关接口")
public class UserController {

    @Autowired
    private UserService service;

    @Autowired
    private MQProducer mqProducer;

    /**
     * 处理用户的登录请求
     */
    @PostApi(value = "/userLogin",auth = false)
    @ApiOperation(value = "登录")
    public String userLogin(@RequestBody User user)
    {
        String token = service.userLogin(user);
        UserRegisteAndLogin.userLogin(user);
        return token;
    }

    @PostApi(value = "/userRegister",auth = false)
    @ApiOperation(value = "注册")
    public String userRegister(@RequestBody User user){
        String password = user.getPassword();

        String[] saltAndCiphertext = UserRegisteAndLogin.encryptPassword(password);

        user.setSalt(saltAndCiphertext[0]);
        user.setPassword(saltAndCiphertext[1]);

        String token = service.userRegister(user);//使用户沆注册后立马登录

        UserRegisteAndLogin.userLogin(user);
        return token;
    }

    @GetApi(value = "/hello")
    @ApiOperation(value = "我是hello")
    public void hello()  {
        System.out.println("hello");
    }

    @GetApi(value = "/sendMsg/{msg}",auth = false)
    @ApiOperation(value = "生产消息")
    public void sendMsg(@PathVariable("msg") String msg){
        mqProducer.sendCustomMsg(msg);
    }
}

