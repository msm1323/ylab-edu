package io.ylab.intensive.lesson05.messagefilter.messagehandle;

import io.ylab.intensive.lesson05.messagefilter.DbClient;
import io.ylab.intensive.lesson05.messagefilter.RabbitClient;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.TimeoutException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageHandlerImpl implements MessageHandler {

    RabbitClient rabbitClient;
    DbClient dbClient;

    MessageHandlerImpl(RabbitClient rabbitClient, DbClient dbClient) {
        this.rabbitClient = rabbitClient;
        this.dbClient = dbClient;
    }

    @Override
    public void start() throws IOException, TimeoutException, SQLException {
        if (dbClient.isTableExists()) {
            dbClient.cleanTable();
        } else {
            dbClient.createTable();
        }
        dbClient.fillTable();
        rabbitClient.listen(this);
    }

    @Override
    public void processing(String message) {
        String separator = "[.,;!? \n\r]";
        String[] words = message.split(separator);
        for (String word : words) {
            if (dbClient.isWordSwear(word)) {
                StringBuilder newWord = new StringBuilder();
                newWord.append(word.charAt(0));
                for (int i = 0; i < word.length() - 2; i++) {
                    newWord.append("*");
                }
                newWord.append(word.charAt(word.length() - 1));
                Pattern p = Pattern.compile(separator + "?" + word + separator + "?");
                Matcher m = p.matcher(message);
                m.find();
                String repl = m.group().replaceAll(word, String.valueOf(newWord));
                message = message.replaceAll(separator + "?" + word + separator + "?", repl);
            }
        }
        sendProcessedMessage(message);
    }

    @Override
    public void sendProcessedMessage(String message) {
        rabbitClient.sendMessage(message, rabbitClient.getQUEUE_OUTPUT());
    }

    @Override
    public void stop() throws IOException {
        rabbitClient.stopListen();
    }

}
