package com.yuan.bi.mq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.Scanner;

public class FanoutProducer {

  private static final String EXCHANGE_NAME = "fanout-exchange";

  public static void main(String[] argv) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");
    try (Connection connection = factory.newConnection();
         Channel channel = connection.createChannel()) {
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        // 创建一个Scanner对象来读取用户输入
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            // 读取用户输入的下一行文本
            String message = scanner.nextLine();
            // 发布消息到交换机
            channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes("UTF-8"));
            // 输出到控制台，表示消息已发送
            System.out.println(" [x] Sent '" + message + "'");
        }
    }
  }
}