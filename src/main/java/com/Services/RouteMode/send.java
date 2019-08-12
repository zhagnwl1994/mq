package com.Services.RouteMode;

import com.Utils.ConnectUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 发布/订阅模式（Publish/Subscribe）
 */
public class send {

    private static String EXCHANGE_ANME = "test_routemode_msg";
    private static String ROUTEMODE_INFO = "info";
    private static String ROUTEMODE_ERROR = "error";
    private static String ROUTEMODE_WARNING = "warning";
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Connection connection = ConnectUtils.getConnection();
        //创建通道
        Channel channel = connection.createChannel();

        //创建交换机 指定模式为 direct
        channel.exchangeDeclare(EXCHANGE_ANME,"direct");

        channel.basicQos(1);

        String info = "info";
        channel.basicPublish(EXCHANGE_ANME,ROUTEMODE_INFO,null,info.getBytes("utf-8"));

        String error = "error";
        channel.basicPublish(EXCHANGE_ANME,ROUTEMODE_ERROR,null,error.getBytes("utf-8"));

        String warning = "warning";
        channel.basicPublish(EXCHANGE_ANME,ROUTEMODE_WARNING,null,warning.getBytes("utf-8"));

        channel.close();
        connection.close();



    }
}
