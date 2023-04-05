package io.ylab.intensive.lesson05.messagefilter;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import io.ylab.intensive.lesson05.messagefilter.messagehandle.MessageHandler;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitClient {

    private ConnectionFactory connectionFactory;
    private Connection consumerConnection;
    private String QUEUE_INPUT;
    private String QUEUE_OUTPUT;

    public String getQUEUE_INPUT() {
        return QUEUE_INPUT;
    }

    public String getQUEUE_OUTPUT() {
        return QUEUE_OUTPUT;
    }

    public void setQUEUE_OUTPUT(String QUEUE_OUTPUT) {
        this.QUEUE_OUTPUT = QUEUE_OUTPUT;
    }

    public void setQUEUE_INPUT(String QUEUE_INPUT) {
        this.QUEUE_INPUT = QUEUE_INPUT;
    }

    public RabbitClient(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public void sendMessage(String message, String queueName) {
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(queueName, false, false, false, null);
            channel.basicPublish("", queueName, null, message.getBytes());
        } catch (IOException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }
    public void listen(MessageHandler messageHandler) throws IOException, TimeoutException {
        consumerConnection = connectionFactory.newConnection();
        Channel channel = consumerConnection.createChannel();
        channel.queueDeclare(QUEUE_INPUT, false, false, false, null);
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            messageHandler.processing(new String(delivery.getBody()));
        };
        channel.basicConsume(QUEUE_INPUT, true, deliverCallback, consumerTag -> {
        });
    }

    public void stopListen() throws IOException {
        consumerConnection.close();
    }

}
