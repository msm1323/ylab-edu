package io.ylab.intensive.lesson05.messagefilter.messagehandle;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.TimeoutException;

public class MessageFilterApp {
  public static void main(String[] args) throws IOException, TimeoutException, SQLException, InterruptedException {
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);
    applicationContext.start();

    MessageHandler messageHandler = applicationContext.getBean(MessageHandler.class);
    messageHandler.start();
//    Thread.sleep(60_000);
//    messageHandler.stop();

  }
}
