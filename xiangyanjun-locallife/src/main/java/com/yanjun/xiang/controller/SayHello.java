package com.yanjun.xiang.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author XiangYanJun
 * @Date 2019/7/23 0023.
 */
@RestController
@RequestMapping("test")
@Api(value = "hello" , tags = "hello")
public class SayHello {

//    @Autowired
//    private RabbitmqConsumer rabbitmqConsumer;

    @GetMapping(value = "hello")
    @ApiOperation(value = "我是hello")
    public void hello() throws IOException, InterruptedException {
//        rabbitmqConsumer.recv();
        System.out.println("hello");
    }
}
