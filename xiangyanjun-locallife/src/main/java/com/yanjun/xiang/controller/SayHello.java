package com.yanjun.xiang.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author XiangYanJun
 * @Date 2019/7/23 0023.
 */
@RestController
@RequestMapping("test")
@Api(value = "hello" , tags = "hello")
public class SayHello {
    @GetMapping(value = "hello")
    @ApiOperation(value = "我是hello")
    public void hello(){
        System.out.println("hello");
    }
}
