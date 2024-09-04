package org.example.consumer;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class ClientRPC implements AutoCloseable {

    private Connection connection;
    private Channel channel;
    private final static String QUEUE_NAME = "rpc_queue";

    public ClientRPC() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        connection = factory.newConnection();
        channel = connection.createChannel();
    }

    public static void main(String[] argv) throws Exception {
        try (ClientRPC fiboRPC = new ClientRPC()){
            for (int i = 0; i < 32; i++) {
                String i_str = Integer.toString(i);
                System.out.println(" [x] Requesting fib("+i_str+")");
                String response = fiboRPC.call(i_str, argv[1]);
                System.out.println(" [.] Got '"+response+"'");
            }
        } catch (IOException | TimeoutException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
    public String call(String message, String name) throws IOException, InterruptedException, ExecutionException {
        final String id = UUID.randomUUID().toString();

        String replyQueueName = channel.queueDeclare().getQueue();
        AMQP.BasicProperties properties = new AMQP.BasicProperties
                .Builder()
                .correlationId(id)
                .replyTo(replyQueueName)
                .build();

        message += "-" + name;

        channel.basicPublish("", QUEUE_NAME, properties, message.getBytes("UTF-8"));

        final CompletableFuture<String> response = new CompletableFuture<>();

        String ctag = channel.basicConsume(replyQueueName, true, (consumerTag, delivery) ->  {
            if (delivery.getProperties().getCorrelationId().equals(id)) {
                response.complete(new String(delivery.getBody(), "UTF-8"));
            }
        }, consumerTag -> {});

        String result = response.get();
        channel.basicCancel(ctag);
        return result;
    }

    @Override
    public void close() throws Exception {
        connection.close();
    }
}
