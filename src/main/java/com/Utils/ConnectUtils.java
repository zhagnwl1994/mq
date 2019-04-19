package com.Utils;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ConnectUtils {


    public static Connection getConnection() throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.56.132");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/test_rbmq");
        connectionFactory.setUsername("test");
        connectionFactory.setPassword("test");

        Connection connection = connectionFactory.newConnection();
        return connection;
    }
}
