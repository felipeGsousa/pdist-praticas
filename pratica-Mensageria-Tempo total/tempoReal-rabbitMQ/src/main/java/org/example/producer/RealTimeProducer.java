package org.example.producer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

public class RealTimeProducer {

    private final static String QUEUE_NAME = "main_queue";

    public static void main(String[] argv) throws Exception{

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()){

            boolean durable = true;
            boolean persistent = false;

            channel.queueDeclare(QUEUE_NAME, durable, false, false, null);

            for (int i = 1; i <= 1000000; i++) {
                long timestamp = System.currentTimeMillis();
                String message = i + "-" + timestamp;
                if (persistent) {
                    channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes("UTF-8"));
                } else {
                    channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
                }
            }
        }
    }

}
