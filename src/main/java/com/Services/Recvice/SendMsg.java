package com.Services.Recvice;

import com.Utils.ConnectUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class SendMsg {
    private static final String QUEUE_NAME="test_simple_queue";
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Connection connection = ConnectUtils.getConnection();
        //创建通道
        Channel channel = connection.createChannel();
        //创建队列
        // 消息持久化
        boolean durable = true;
       channel.queueDeclare(QUEUE_NAME,durable,false,false,null);
        //每次向同一个队列只发送一条消息
        channel.basicQos(1);

        for(int i = 0 ;i<50;i++){
            String msg = "this is simple queue:"+i;
            System.out.println("send:"+msg);
            //MessageProperties.PERSISTENT_TEXT_PLAIN 设置消息的持久化
            channel.basicPublish("",QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN,msg.getBytes("utf-8"));
            Thread.sleep(50);
        }

       try {

       }finally {
           channel.close();;
           connection.close();

       }
    }
}
