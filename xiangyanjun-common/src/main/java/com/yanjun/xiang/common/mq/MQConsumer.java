package com.yanjun.xiang.common.mq;

import com.rabbitmq.client.Channel;
import com.yanjun.xiang.common.configuration.RabbitMQConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class MQConsumer {

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("queue.topic.say.hello"),
            exchange = @Exchange(value = "exchange.topic.say.hello", type = ExchangeTypes.TOPIC),
            key = "hello"))
    @RabbitHandler
    public void process(Message message, Channel channel) throws IOException {
        String info = new String(message.getBody(), "UTF-8");
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        System.out.println(deliveryTag);
        channel.basicAck(deliveryTag,false);
        System.out.println(info);
    }

    @RabbitListener(queues = RabbitMQConfig.BUSINESS_QUEUEA_NAME)
    public void receiveA(Message message, Channel channel) throws IOException {
        String msg = new String(message.getBody());
        log.info("收到业务消息A：{}", msg);
        boolean ack = true;
        Exception exception = null;
        try {
            if (msg.contains("deadletter")){
                throw new RuntimeException("dead letter exception");
            }
        } catch (Exception e){
            ack = false;
            exception = e;
        }
        if (!ack){
            log.error("消息消费发生异常，error msg:{}", exception.getMessage(), exception);
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
        } else {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }
    }

    @RabbitListener(queues = RabbitMQConfig.BUSINESS_QUEUEB_NAME)
    public void receiveB(Message message, Channel channel) throws IOException {
        System.out.println("收到业务消息B：" + new String(message.getBody()));
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }



}
