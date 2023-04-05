package io.ylab.intensive.lesson05.eventsourcing.db;

import io.ylab.intensive.lesson05.eventsourcing.Message;
import io.ylab.intensive.lesson05.eventsourcing.Person;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public interface DataProcessor {

    void deletePerson(Long personId);

    void savePerson(Person person);

    void start() throws IOException, TimeoutException;

    void processing(Message message);

}
