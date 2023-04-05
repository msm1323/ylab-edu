package io.ylab.intensive.lesson05.eventsourcing;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.*;
import io.ylab.intensive.lesson05.eventsourcing.db.DataProcessor;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitClient {

    ConnectionFactory connectionFactory;
    ObjectMapper objectMapper;

    private String QUEUE_NAME;

    public void setQUEUE_NAME(String QUEUE_NAME) {
        this.QUEUE_NAME = QUEUE_NAME;
    }

    public RabbitClient(ConnectionFactory connectionFactory, ObjectMapper objectMapper) {
        this.connectionFactory = connectionFactory;
        this.objectMapper = objectMapper;
    }

    public void sendMessage(Message message) {
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            channel.basicPublish("", QUEUE_NAME, null, objectMapper.writeValueAsBytes(message));
        } catch (IOException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

    public void listen(DataProcessor dataProcessor) throws IOException, TimeoutException {
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            dataProcessor.processing(objectMapper.readValue(delivery.getBody(), Message.class));
        };
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {
        });

    }

}
