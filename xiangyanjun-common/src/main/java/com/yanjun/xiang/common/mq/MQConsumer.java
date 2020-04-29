package com.yanjun.xiang.common.mq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@Component
public class MQConsumer {

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("queue.topic.say.hello"),
            exchange = @Exchange(value = "exchange.topic.say.hello", type = ExchangeTypes.TOPIC),
            key = "hello"))
    @RabbitHandler
    public void process(Message message, Channel channel) throws UnsupportedEncodingException {
        String info = new String(message.getBody(), "UTF-8");
        System.out.println(info);
    }

}
