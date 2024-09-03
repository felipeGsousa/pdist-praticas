import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;


public class Consumidor {
    private static final String TASK_QUEUE_NAME = "task_queue";

    public static void main(String[] args) throws Exception {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        final Connection conexao = connectionFactory.newConnection();
        final Channel canal = conexao.createChannel();

        canal.queueDeclare(TASK_QUEUE_NAME, false, false, false, null);
        System.out.println ("[*] Aguardando mensagens. Para sair, pressione CTRL + C");

        canal.basicQos(1);

        DeliverCallback callback = (consumerTag, delivery) -> {
            String mensagem = new String(delivery.getBody(), "UTF-8");
            System.out.println("[x] Recebido " + mensagem + "");
            try {
                try {
                    doWork(mensagem);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } finally {
                System.out.println("[x] Feito");
                canal.basicAck(delivery.getEnvelope().getDeliveryTag(),false);
            }
        };

        canal.basicConsume(TASK_QUEUE_NAME, false, callback, consumerTag -> {
        });
    }

    private static void doWork(String task) throws InterruptedException {
        for (char ch: task.toCharArray()) {
            if (ch == '.') {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException _ignored){
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}


