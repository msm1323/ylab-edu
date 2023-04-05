package io.ylab.intensive.lesson05.messagefilter.publisher;

import com.rabbitmq.client.ConnectionFactory;
import io.ylab.intensive.lesson05.messagefilter.RabbitClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public ConnectionFactory connectionFactory() {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setVirtualHost("/");
        return connectionFactory;
    }

    @Bean
    public RabbitClient rabbitClient(){
        RabbitClient rabbitClient = new RabbitClient(connectionFactory());
        rabbitClient.setQUEUE_INPUT("input");
        rabbitClient.setQUEUE_OUTPUT("output");
        return rabbitClient;
    }

}
