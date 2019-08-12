package com.Services.topicModel;

import com.Utils.ConnectUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class recv3 {
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectUtils.getConnection();

        Channel channel = connection.createChannel();
        channel.queueDeclare("quene_student",false,false,false,null);
        channel.queueBind("quene_student","top_exchange","students_select");

        channel.basicQos(1);

        DefaultConsumer defaultConsumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                long deliveryTag = envelope.getDeliveryTag();

                System.out.println("recv3:"+new String(body));
                channel.basicAck(deliveryTag,false); //手動回執
            }
        };

        channel.basicConsume("quene_student",defaultConsumer);

    }
}
