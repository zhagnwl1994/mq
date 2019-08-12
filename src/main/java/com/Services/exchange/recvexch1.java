package com.Services.exchange;

import com.Utils.ConnectUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class recvexch1 {
    private static String EXCHANGE_ANME=  "test_exchange";
    private static String QUEUE_NAME = "TEST_EXCHANGE1";
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectUtils.getConnection();
        Channel channel = connection.createChannel();
        //声明队列
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        //将队列绑定到交换机
        channel.queueBind(QUEUE_NAME,EXCHANGE_ANME,"") ;
        //向同一个消费者,每次只发送一条数据
        channel.basicQos(1);

        DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body);
                System.out.println("exchange[1]:" + msg);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //手动回执
                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        };

        //监听队列
        //设置autoAck = false, 关闭自定应答
        channel.basicConsume(QUEUE_NAME,false,defaultConsumer);

    }
}
