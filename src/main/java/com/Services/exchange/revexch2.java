package com.Services.exchange;

import com.Utils.ConnectUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class revexch2 {
    private static String EXCHANGE_ANME=  "test_exchange";
    private static String QUEUE_NAME = "TEST_EXCHANGE2";
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectUtils.getConnection();

        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME,true,false,false,null);

        channel.queueBind(QUEUE_NAME,EXCHANGE_ANME,null);

        



    }

}
