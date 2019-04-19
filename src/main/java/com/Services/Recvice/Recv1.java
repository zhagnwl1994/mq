package com.Services.Recvice;

import com.Utils.ConnectUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Recv1 {
    private static final String QUEUE_NAME = "test_simple_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectUtils.getConnection();

        Channel channel = connection.createChannel();
        //声明队列
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        //每次只接收一条消息
        channel.basicQos(1);
        DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String delivery = new String(body);
                System.out.println("[1]:"+delivery);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //手动回执
                System.out.println("[1]envelope.getDeliveryTag():"+envelope.getDeliveryTag());
                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        };

        //关闭自动应答
        boolean autoAck = false;
        // 监听队列
        channel.basicConsume(QUEUE_NAME,autoAck,defaultConsumer);
    }
}
