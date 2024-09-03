import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

public class Produtor {
    private static final String TASK_QUEUE_NAME = "task_queue";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        try (
                Connection connection = connectionFactory.newConnection();
                Channel canal = connection.createChannel();
        ) {
            //(queue, passive, durable, exclusive, autoDelete, arguments)
            canal.queueDeclare(TASK_QUEUE_NAME, false, false, false, null);

            String mensagem = String.join(" ", argv);
            mensagem = mensagem + " Felipe Galdino de Sousa";

            canal.basicPublish("", TASK_QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, mensagem.getBytes("UTF-8"));
            System.out.println("[x] Enviado " + mensagem + " ");

        }
    }
}


