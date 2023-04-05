package io.ylab.intensive.lesson05.messagefilter.messagehandle;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.TimeoutException;

public interface MessageHandler {

    void start() throws IOException, TimeoutException, SQLException;

    void processing(String message);

    void sendProcessedMessage(String message);

    void stop() throws IOException;

}
