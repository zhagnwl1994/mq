package com.Services.exchange;

import com.Utils.ConnectUtils;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 发布/订阅模式（Publish/Subscribe）
 */
public class send {

    private static String EXCHANGE_ANME=  "test_exchange";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Connection connection = ConnectUtils.getConnection();
        //创建通道
        Channel channel = connection.createChannel();
        //主要 用来检测相应的交换器是否存在,如果存在就正常返回,不存就是抛出异常404 channel exception,同是channel也会被关闭
       // AMQP.Exchange.DeclareOk declareOk = channel.exchangeDeclarePassive(EXCHANGE_ANME);
        // System.out.println("AMQP.Exchange.DeclareOk:"+declareOk);
        channel.exchangeDeclare(EXCHANGE_ANME,"fanout");

        for (int i= 0;i<10;i++){
            String msg = "发布/订阅模式（Publish/Subscribe）:"+i;
            channel.basicPublish(EXCHANGE_ANME,"", MessageProperties.PERSISTENT_TEXT_PLAIN,msg.getBytes("utf-8"));
            System.out.println(msg);
            Thread.sleep(500);
        }
       try {

       }finally {
           channel.close();
           connection.close();
       }
        
    }
}
