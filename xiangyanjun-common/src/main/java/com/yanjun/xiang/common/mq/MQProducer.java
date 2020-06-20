package com.yanjun.xiang.common.mq;

import com.yanjun.xiang.common.configuration.RabbitMQConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.UUID;

@Component
@Slf4j
public class MQProducer implements RabbitTemplate.ConfirmCallback {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    //PostConstruct会在依赖注入完成后被自动调用
    @PostConstruct
    private void init(){
//        rabbitTemplate.setChannelTransacted(true);
        rabbitTemplate.setConfirmCallback(this);
    }

//    @Transactional
//    public void sendMsg(String msg){
//        rabbitTemplate.convertAndSend(RabbitMQConfig.BUSINESS_EXCHANGE_NAME,"key",msg);
//        log.info("msg:{}", msg);
//        if (msg != null && msg.contains("exception"))
//            throw new RuntimeException("surprise!");
//        log.info("消息已发送 {}" ,msg);
//    }

    public void sendCustomMsg(String msg) {
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());

        log.info("消息id:{}, msg:{}", correlationData.getId(), msg);

        rabbitTemplate.convertAndSend(RabbitMQConfig.BUSINESS_EXCHANGE_NAME, "key", msg, correlationData);
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean b, String s) {
        String id = correlationData != null ? correlationData.getId() : "";
        if (b) {
            log.info("消息确认成功, id:{}", id);
        } else {
            log.error("消息未成功投递, id:{}, cause:{}", id, s);
        }
    }
}
