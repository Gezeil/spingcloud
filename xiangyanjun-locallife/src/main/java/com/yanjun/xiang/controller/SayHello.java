package com.yanjun.xiang.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.UUID;

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

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping(value = "hello")
    @ApiOperation(value = "我是hello")
    public void hello()  {
//        rabbitmqConsumer.recv();
        rabbitTemplate.convertAndSend("exchange.topic.say.hello", "hello","hello",new CorrelationData(UUID.randomUUID().toString()));
        System.out.println("hello");
    }

}
