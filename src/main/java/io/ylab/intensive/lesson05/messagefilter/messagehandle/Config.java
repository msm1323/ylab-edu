package io.ylab.intensive.lesson05.messagefilter.messagehandle;

import javax.sql.DataSource;

import com.rabbitmq.client.ConnectionFactory;
import io.ylab.intensive.lesson05.messagefilter.DbClient;
import io.ylab.intensive.lesson05.messagefilter.RabbitClient;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.sql.SQLException;

@Configuration
//@ComponentScan("io.ylab.intensive.lesson05.messagefilter.messagehandle")
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
  public DataSource dataSource() throws SQLException {
    PGSimpleDataSource dataSource = new PGSimpleDataSource();
    dataSource.setServerName("localhost");
    dataSource.setUser("postgres");
    dataSource.setPassword("postgres");
    dataSource.setDatabaseName("postgres");
    dataSource.setPortNumber(5432);
    return dataSource;
  }

  @Bean
  public RabbitClient rabbitClient(){
    RabbitClient rabbitClient = new RabbitClient(connectionFactory());
    rabbitClient.setQUEUE_INPUT("input");
    rabbitClient.setQUEUE_OUTPUT("output");
    return rabbitClient;
  }

  @Bean
  public DbClient dbClient() throws SQLException {
    DbClient dbClient = new DbClient(dataSource());
    dbClient.setTableName("swear_words");
    dbClient.setColumnName("word");
    dbClient.setSwearWordsFile(new File("src/main/java/io/ylab/intensive/lesson05/messagefilter/swear_words.txt"));
    return dbClient;
  }

  @Bean
  public MessageHandler messageHandler() throws SQLException {
    return new MessageHandlerImpl(rabbitClient(), dbClient());
  }

}
