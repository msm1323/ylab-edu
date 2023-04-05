package io.ylab.intensive.lesson05.eventsourcing.api;

import javax.sql.DataSource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.ConnectionFactory;
import io.ylab.intensive.lesson05.eventsourcing.DbClient;
import io.ylab.intensive.lesson05.eventsourcing.RabbitClient;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
  
  @Bean
  public DataSource dataSource() {
    PGSimpleDataSource dataSource = new PGSimpleDataSource();
    dataSource.setServerName("localhost");
    dataSource.setUser("postgres");
    dataSource.setPassword("postgres");
    dataSource.setDatabaseName("postgres");
    dataSource.setPortNumber(5432);
    return dataSource;
  }

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
  public ObjectMapper objectMapper(){
    return new ObjectMapper();
  }

  @Bean
  public RabbitClient rabbitClient(){
    RabbitClient rabbitClient = new RabbitClient(connectionFactory(), objectMapper());
    rabbitClient.setQUEUE_NAME("queue");
    return rabbitClient;
  }

  @Bean
  public DbClient dbClient(){
    return new DbClient(dataSource());
  }

  @Bean
  public PersonApiImpl personApi(){
    return new PersonApiImpl(dbClient(), rabbitClient());
  }

}
