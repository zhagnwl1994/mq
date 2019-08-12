package com.Services.topicModel;

import com.Utils.ConnectUtils;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class send {

    private static final String TOPIC_EXCHANGE = "exchange_topic";
    public static void main(String[] args) throws IOException, TimeoutException {

        Connection connection = ConnectUtils.getConnection();

        Channel channel = connection.createChannel();

        channel.exchangeDeclare(TOPIC_EXCHANGE, BuiltinExchangeType.TOPIC);

        channel.basicQos(1);

        String msg_select = "goods_select";
        channel.basicPublish(TOPIC_EXCHANGE,"goods.add",null,msg_select.getBytes());
        System.out.println("Send msg:"+msg_select);
        String msg_update = "goods_update";
        channel.basicPublish(TOPIC_EXCHANGE,"goods.update",null,msg_update.getBytes());

        System.out.println("Send msg:"+msg_update);
        try {

        }finally {
            channel.close();
            connection.close();
        }
    }

    public void send2() throws IOException, TimeoutException {

        Connection connection = ConnectUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare("top_exchange", BuiltinExchangeType.TOPIC);
        channel.basicQos(1);

        String mgs_select = "students_select";
        //发送
        channel.basicPublish("top_exchange","students_select",null,mgs_select.getBytes());
        System.out.println("Send msg:"+mgs_select);
        try {

        }finally {
            channel.close();
            connection.close();
        }
    }
}
