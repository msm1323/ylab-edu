package io.ylab.intensive.lesson04.eventsourcing.api;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import io.ylab.intensive.lesson04.eventsourcing.Message;
import io.ylab.intensive.lesson04.eventsourcing.Person;
import io.ylab.intensive.lesson04.eventsourcing.Query;

import javax.sql.DataSource;

public class PersonApiImpl implements PersonApi {

    private DataSource dataSource;
    private ConnectionFactory connectionFactory;
    private ObjectMapper objectMapper;

    private String QUEUE_NAME = "apiAppQueue";
//    private String EXCHANGE_NAME = "exc";

    PersonApiImpl(DataSource dataSource, ConnectionFactory connectionFactory) {
        this.dataSource = dataSource;
        this.connectionFactory = connectionFactory;
    }

    @Override
    public void deletePerson(Long personId) {
        Person person = new Person();
        person.setId(personId);
        sendMessage(person, Query.DELETE);
    }

    @Override
    public void savePerson(Long personId, String firstName, String lastName, String middleName) {
        Person person = new Person(personId, firstName, lastName, middleName);
        sendMessage(person, Query.SAVE);
    }

    private void sendMessage(Person person, Query query) {
        objectMapper = new ObjectMapper();
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {
//            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
//            channel.queueBind(QUEUE_NAME, EXCHANGE_NAME,"*");

            Message message = new Message(person, query);
            channel.basicPublish("", QUEUE_NAME, null, objectMapper.writeValueAsBytes(message));
        } catch (IOException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Person findPerson(Long personId) {
        String findPersonQuery = "SELECT * FROM person WHERE person_id = ?";
        Person person = null;
        try (java.sql.Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(findPersonQuery)) {
            preparedStatement.setLong(1, personId);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                person = new Person(personId, rs.getString(2), rs.getString(3), rs.getString(4));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return person;
    }

    @Override
    public List<Person> findAll() {
        String findPersonQuery = "SELECT * FROM person";
        List<Person> persons = new ArrayList<>();
        try (java.sql.Connection connection = dataSource.getConnection();
             Statement preparedStatement = connection.createStatement()) {
            ResultSet rs = preparedStatement.executeQuery(findPersonQuery);
            while (rs.next()) {
                persons.add(new Person(rs.getLong(1), rs.getString(2),
                        rs.getString(3), rs.getString(4)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return persons;
    }
}
