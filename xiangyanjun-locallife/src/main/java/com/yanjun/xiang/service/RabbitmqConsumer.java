//package com.yanjun.xiang.service;
//
//
//import com.rabbitmq.client.Channel;
//import com.rabbitmq.client.Connection;
//import com.rabbitmq.client.QueueingConsumer;
//import com.yanjun.xiang.util.ConnectionUtil;
//import org.springframework.stereotype.Service;
//
//import java.io.IOException;
//
//public class RabbitmqConsumer  {
//
//    private final static String QUEUE_NAME = "q_test_01";
//
////    public static void main(String[] args) throws Exception {
////        // 获取到连接以及mq通道
////        Connection connection = ConnectionUtil.getConnection();
////        // 从连接中创建通道
////        Channel channel = connection.createChannel();
////        // 声明队列
////        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
////
////        // 定义队列的消费者
////        QueueingConsumer consumer = new QueueingConsumer(channel);
////
////        // 监听队列
////        channel.basicConsume(QUEUE_NAME, true, consumer);
////
////        // 获取消息
////        while (true) {
////            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
////            String message = new String(delivery.getBody());
////            System.out.println(" [x] Received '" + message + "'");
////        }
////    }
//
//    public void recv() throws IOException, InterruptedException {
//        Connection connection = ConnectionUtil.getConnection();
//        Channel channel = connection.createChannel();
//        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
//        QueueingConsumer consumer = new QueueingConsumer(channel);
//        channel.basicConsume(QUEUE_NAME,true,consumer);
//        while (true){
//            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
//            String s = new String(delivery.getBody());
//            System.out.println(s);
//        }
//    }
//
//}
