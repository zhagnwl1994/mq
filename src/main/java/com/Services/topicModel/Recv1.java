package com.Services.topicModel;

import com.Utils.ConnectUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Recv1 {

    private static final String TOPIC_EXCHANGE = "exchange_topic";
    private static String QUEUE_ANME = "test_topic1_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectUtils.getConnection();

        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_ANME,false,false,false,null);
/*        String queueName = channel.queueDeclare().getQueue();
        System.out.println("queueName:"+queueName);*/
        channel.basicQos(1);
        channel.queueBind(QUEUE_ANME,TOPIC_EXCHANGE,"goods.add");

        DefaultConsumer defaultConsumer = new DefaultConsumer(channel){

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                long deliveryTag = envelope.getDeliveryTag();

                System.out.println("topic_recv1:"+new String(body));
                //手动回执
                channel.basicAck(deliveryTag,false);
            }
        };
        //监听 队列
        channel.basicConsume(QUEUE_ANME,true,defaultConsumer);
        //channel.close();
    }
}
