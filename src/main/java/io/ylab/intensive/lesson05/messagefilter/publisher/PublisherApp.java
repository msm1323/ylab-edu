package io.ylab.intensive.lesson05.messagefilter.publisher;

import io.ylab.intensive.lesson05.messagefilter.RabbitClient;
import io.ylab.intensive.lesson05.messagefilter.messagehandle.Config;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class PublisherApp {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);
        applicationContext.start();
        RabbitClient rabbitClient = applicationContext.getBean(RabbitClient.class);
        String message = "шараёбиться Крас х_У_я_р_А ота какая, ебать-копать! b3ъEб\n хуячить;хуясевар варенье ху ясе " +
                "cock чмырь. ?6лядь\n варенье \rebaL 6лядЬ ";
        rabbitClient.sendMessage(message, rabbitClient.getQUEUE_INPUT());
    }

}