package com.yanjun.xiang.common.conroller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author XiangYanJun
 * @Date 2019/7/24 0024.
 */
@RestController
@RequestMapping("test")
@Api(value = "hello2" , tags = "hello2")
public class CommonController {
    @GetMapping("hello2")
    @ApiOperation(value = "我是hello2")
    public void hello(){
        System.out.println("我是hello2");
    }
}
