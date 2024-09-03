package org.example.consumer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class HelloConsumer {
    public static void main(String[] args) throws Exception {
        System.out.println("Consumidor");
        String NOME_FILA = "filaOla";
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(NOME_FILA, false, false, false, null);
        DeliverCallback callback = (consumerTag , delivery) -> {
            String message = new String(delivery.getBody());
            System.out.println("Recebi a mensagem: " + message);
        };
        channel.basicConsume(NOME_FILA, true, callback, consumerTag -> {});
        System.out.println("Continuarei executando outras atividades enquanto não chega mensagem...");
    }
}
