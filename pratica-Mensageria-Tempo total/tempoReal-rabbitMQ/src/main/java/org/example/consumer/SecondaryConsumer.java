package org.example.consumer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.nio.charset.StandardCharsets;

public class SecondaryConsumer {

    private final static String SECONDARY_QUEUE = "filtered_queue";
    private static long Timestamp = 0;

    public static void main(String[] argv) throws Exception{
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(SECONDARY_QUEUE, false, false, false, null);

        DeliverCallback callback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            String[] messageParts = message.split("-");
            if (Integer.parseInt(messageParts[0]) == 1) {
                Timestamp = Long.parseLong(messageParts[1]);
            } else {
                long timestamp = Long.parseLong(messageParts[1]);
                long diffTimestamp = timestamp - Timestamp;
                System.out.println("Latencia entre 1 e 1000000: " + diffTimestamp + "ms");
            }
        };

        channel.basicConsume(SECONDARY_QUEUE, true, callback, consumerTag -> {});
    }
}
