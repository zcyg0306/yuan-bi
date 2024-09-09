package com.yuan.bi.mq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class TopicConsumer {

    private static final String EXCHANGE_NAME = "topic-exchange";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "topic");

        // 创建前端队列
        String queueName = "frontend_queue";
        // 声明队列，设置队列为持久化的，非独占的，非自动删除的
        channel.queueDeclare(queueName, true, false, false, null);
        // 将队列绑定到交换机,使用路由键模式"#.前端.#"
        channel.queueBind(queueName, EXCHANGE_NAME, "#.前端.#");

        // 创建后端队列
        String queueName2 = "backend_queue";
        // 声明队列，设置队列为持久化的，非独占的，非自动删除的
        channel.queueDeclare(queueName2, true, false, false, null);
        // 将队列绑定到交换机，使用路由键模式"#.后端.#"
        channel.queueBind(queueName2, EXCHANGE_NAME, "#.后端.#");

        // 创建产品队列
        String queueName3 = "product_queue";
        // 声明队列，设置队列为持久化的，非独占的，非自动删除的
        channel.queueDeclare(queueName3, true, false, false, null);
        // 将队列绑定到交换机，使用路由键模式"#.产品.#"
        channel.queueBind(queueName3, EXCHANGE_NAME, "#.产品.#");
        // 打印等待消息的提示信息
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");



        // 创建一个DeliverCallback对象，用于处理接收到的消息（xiaoa）
        DeliverCallback xiaoaDeliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [xiaoa] Received '" +
                    delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");
        };

        // 创建一个DeliverCallback对象，用于处理接收到的消息（xiaob）
        DeliverCallback xiaobDeliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [xiaob] Received '" +
                    delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");
        };

        // 创建一个DeliverCallback对象，用于处理接收到的消息（xiaoc）
        DeliverCallback xiaocDeliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [xiaoc] Received '" +
                    delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");
        };

        // 启动消费者并绑定消息处理的回调函数到各个队列上
        // 开始消费队列中的消息（xiaoa），设置自动确认消息已被消费
        channel.basicConsume(queueName, true, xiaoaDeliverCallback, consumerTag -> { });
        // 开始消费队列中的消息（xiaob），设置自动确认消息已被消费
        channel.basicConsume(queueName2, true, xiaobDeliverCallback, consumerTag -> { });
        // 开始消费队列中的消息（xiaoc），设置自动确认消息已被消费
        channel.basicConsume(queueName3, true, xiaocDeliverCallback, consumerTag -> { });
    }
}