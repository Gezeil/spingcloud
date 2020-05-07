package com.yanjun.xiang.common.conroller;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.yanjun.xiang.common.util.ConnectionUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.spin.common.web.annotation.PostApi;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@RestController
@RequestMapping(value = "/rabbit")
@Api(value = "rabbit" , tags = "rabbit")
public class ProviderController {

    private final String QUEUE_NAME = "q_test_01";

    @PostApi(value = "/send" , auth = false)
    @ApiOperation(value = "发送")
    public void send() throws Exception {
        // 获取到连接以及mq通道
        Connection connection = ConnectionUtil.getConnection();
        // 从连接中创建通道
        Channel channel = connection.createChannel();

        // 声明（创建）队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        // 消息内容
        String message = "Hello World!";
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");
        //关闭通道和连接
        channel.close();
        connection.close();
    }

}
