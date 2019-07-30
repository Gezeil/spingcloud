package com.yanjun.xiang.common.conroller;

import com.yanjun.xiang.common.Entity.Login;
import com.yanjun.xiang.common.annotation.Auth;
import com.yanjun.xiang.common.annotation.PostApi;
import com.yanjun.xiang.common.util.JwtUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author XiangYanJun
 * @Date 2019/7/30 0030.
 */
@RestController
@RequestMapping("user")
@Api(tags = "用户相关接口")
public class LoginController {

    @ApiOperation(value = "登录接口")
    @PostApi(value = "/login",auth = false)
    public String login(Login login){
        boolean isSuccess=false;
        if (login.getLoginName().equals("1")&&login.getPassword().equals("1")){
            isSuccess=true;
        }
        if (isSuccess){
            String token = JwtUtils.sign(login.getLoginName(), "1");
            if (token!=null){
                return token;
            }
        }
        return "登录失败";
    }
}
