package org.example.consumer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.nio.charset.StandardCharsets;

public class RealTimeConsumer {
    private final static String QUEUE_NAME = "main_queue";
    private final static String SECONDARY_QUEUE = "filtered_queue";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        Channel channel2 = connection.createChannel();
        boolean durable = true;
        channel.queueDeclare(QUEUE_NAME, durable, false, false, null);
        channel2.queueDeclare(SECONDARY_QUEUE, false, false, false, null);

        DeliverCallback callback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            String[] messageParts = message.split("-");
            int messageNumber = Integer.parseInt(messageParts[0]);
            long sentTimestamp = Long.parseLong(messageParts[1]);
            long receivedTimestamp = System.currentTimeMillis();
            long diffTimestamp = receivedTimestamp - sentTimestamp;

            System.out.println("Mensagem: " + message + ", Latencia: " + diffTimestamp + "ms");
            if (messageNumber == 1 || messageNumber == 1000000) {
                channel2.basicPublish("", SECONDARY_QUEUE, null, message.getBytes("UTF-8"));
            }

            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        };

        channel.basicConsume(QUEUE_NAME, false, callback, consumerTag -> {});
    }
}
