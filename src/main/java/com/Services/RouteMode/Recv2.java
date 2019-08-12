package com.Services.RouteMode;

import com.Utils.ConnectUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Recv2 {
    private static String EXCHANGE_ANME = "test_routemode_msg";
    private static String QUEUE_ANME = "test_queue_msg2";

    private static String ROUTEMODE_INFO = "info";
    private static String ROUTEMODE_ERROR = "error";
    private static String ROUTEMODE_WARNING = "warning";
    public static void main(String[] args) throws IOException, TimeoutException {
        {
            Connection connection = ConnectUtils.getConnection();

            Channel channel = connection.createChannel();

            channel.queueDeclare(QUEUE_ANME,false,false,false,null);
            //保证一次只分发一个
            channel.basicQos(1);

            //将队列绑定 交换机,并绑定多个 接收标识
            channel.queueBind(QUEUE_ANME,EXCHANGE_ANME,ROUTEMODE_INFO);
            channel.queueBind(QUEUE_ANME,EXCHANGE_ANME,ROUTEMODE_ERROR);
            channel.queueBind(QUEUE_ANME,EXCHANGE_ANME,ROUTEMODE_WARNING);


            //创建 消费者
            DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    System.out.println(new String(body));
                    //手动回执
                    channel.basicAck(envelope.getDeliveryTag(),false);

                }
            };
            //监听
            channel.basicConsume(QUEUE_ANME,true,defaultConsumer);

        }
    }
}
