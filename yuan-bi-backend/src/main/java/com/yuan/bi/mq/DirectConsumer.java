package com.yuan.bi.mq;

import com.rabbitmq.client.*;

public class DirectConsumer {

  private static final String EXCHANGE_NAME = "direct-exchange";

  public static void main(String[] argv) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    channel.exchangeDeclare(EXCHANGE_NAME, "direct");

    // 创建队列，随机分配一个队列名称，并绑定到“Peter”路由键
    String queueName = "Peter";
    // 声明队列，设置队列为持久化的，非独占的，非自动删除的
    channel.queueDeclare(queueName, true, false, false, null);
    // 将队列绑定到交换机，绑定键为“Peter”
    channel.queueBind(queueName, EXCHANGE_NAME, "Peter");

    // 创建队列，随机分配一个队列名称，并绑定到“Tony”路由键
    String queueName2 = "Tony";
    // 声明队列，设置队列为持久化的，非独占的，非自动删除的
    channel.queueDeclare(queueName2, true, false, false, null);
    // 将队列绑定到交换机，绑定键为“Tony”
    channel.queueBind(queueName2, EXCHANGE_NAME, "Tony");
    // 打印等待消息的提示信息
    System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

    // 创建一个DeliverCallback对象，用于处理接收到的消息（Peter）
    DeliverCallback peterDeliverCallback = (consumerTag, delivery) -> {
        String message = new String(delivery.getBody(), "UTF-8");
        System.out.println(" [Peter] Received '" +
            delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");
    };

    // 创建一个DeliverCallback对象，用于处理接收到的消息（Tony）
    DeliverCallback tonyDeliverCallback = (consumerTag, delivery) -> {
        String message = new String(delivery.getBody(), "UTF-8");
        System.out.println(" [Tony] Received '" +
            delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");
    };

    // 开始消费队列中的消息（Peter），设置自动确认消息已被消费
    channel.basicConsume(queueName, true, peterDeliverCallback, consumerTag -> { });
    // 开始消费队列中的消息（Tony），设置自动确认消息已被消费
    channel.basicConsume(queueName2, true, tonyDeliverCallback, consumerTag -> { });
  }
}