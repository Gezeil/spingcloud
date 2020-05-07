package com.yanjun.xiang.common.conroller;

import com.yanjun.xiang.common.entity.User;
import com.yanjun.xiang.common.service.UserService;
import com.yanjun.xiang.common.util.UserRegisteAndLogin;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.spin.common.web.annotation.GetApi;
import org.spin.common.web.annotation.PostApi;
import org.spin.core.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
@Api(tags = "用户相关接口")
public class UserController {

    @Autowired
    private UserService service;

    public static void main(String[] args) {
        String code = "eyJhbGciOiJFUzUxMiJ9.eyJleHAiOjE1ODg5MjM1OTIsInN1YiI6IjI0NjY2NjoxNTExMjY0MzEzNyJ9.ADiGVJhC7Bz7KOn67aesIBMCO5R8foouAjlj5p_x5VXKUpPQrNPhHWLxIGuqhgrvEufXOHmbqCY25RhYCbuvS8kGAfGoKHAOpFPWvTswugH0Eh61cQbgKCZavTBdOpEsLdsKtcp29k8d3x9BQMGXh200DK7OD6II1mJ-JYpCViSu53Nk";
        String s = StringUtils.urlDecode(code);
        System.out.println(s);
//        int[] ints = new int[]{5, 77, 33, 88, 55, 34, 76, 89, 32, 12, 23, 45, 25, 83, 1, 6};
//        int i,j;
//        for (i=0;i<ints.length-1;i++){
//            for (j=0;j<ints.length-1-i;j++){
//                if (ints[j]>ints[j+1]){
//                    int a = ints[j];
//                    ints[j] = ints[j+1];
//                    ints[j+1] = a;
//                }
//            }
//        }
//        for (i=0;i<ints.length;i++){
//            System.out.println(ints[i]);
//        }
    }

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

}

