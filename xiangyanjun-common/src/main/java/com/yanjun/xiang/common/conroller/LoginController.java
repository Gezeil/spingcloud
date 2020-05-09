package com.yanjun.xiang.common.conroller;

import com.yanjun.xiang.common.entity.Login;
import com.yanjun.xiang.common.util.JwtUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.spin.common.web.annotation.PostApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * @author XiangYanJun
 * @Date 2019/7/30 0030.
 */
@RestController
@RequestMapping("user")
@Api(tags = "登录相关接口")
public class LoginController {

    @Autowired
    private RedisTemplate redisTemplate;

    @ApiOperation(value = "登录接口")
    @PostApi(value = "/login",auth = false)
    public String login(@RequestBody Login login){
        boolean isSuccess=false;
        if (login.getLoginName().equals("1")&&login.getPassword().equals("1")){
            isSuccess=true;
        }
        if (isSuccess){
            String token = JwtUtils.sign(login.getLoginName(), 1L);
            if (token!=null){
                redisTemplate.opsForValue().set(token,login.getLoginName());
                redisTemplate.boundValueOps(token).set(token,1, TimeUnit.DAYS);
                return token;
            }
        }
        return "登录失败";
    }
}
