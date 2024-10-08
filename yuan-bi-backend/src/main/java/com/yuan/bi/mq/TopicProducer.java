package com.yuan.bi.mq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.Scanner;

public class TopicProducer {

    private static final String EXCHANGE_NAME = "topic-exchange";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            // 声明使用主题交换机，并指定交换机名称和类型
            channel.exchangeDeclare(EXCHANGE_NAME, "topic");
            // 创建一个Scanner对象用于读取用户输入
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNext()) {
                // 读取用户输入的下一行文本,并以空格分割
                String userInput = scanner.nextLine();
                String[] strings = userInput.split(" ");

                // 如果输入内容不符合要求，继续读取下一行
                if (strings.length < 1) {
                    continue;
                }

                // 获取消息内容和路由键
                String message = strings[0];
                String routingKey = strings[1];
                // 发布消息到主题交换机和路由键
                channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes("UTF-8"));
                // 打印成功发送的消息信息，包括消息内容和路由键
                System.out.println(" [x] Sent '" + message + "'with routing:'" + routingKey + "'");
            }
        }
    }
    //..
}