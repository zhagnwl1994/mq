package com.Services;

import com.Utils.ConnectUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class SendMsg {
    private static final String QUEUE_NAME="test_simple_queue";
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectUtils.getConnection();
        //创建通道
        Channel channel = connection.createChannel();
        //创建队列
       channel.queueDeclare(QUEUE_NAME,false,false,false,null);
       String msg = "this is simple queue";
        System.out.println("send:"+msg);
       channel.basicPublish("",QUEUE_NAME,null,msg.getBytes("utf-8"));

       try {

       }finally {
           channel.close();;
           connection.close();

       }
    }
}
