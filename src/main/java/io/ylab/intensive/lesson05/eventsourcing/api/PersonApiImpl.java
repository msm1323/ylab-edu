package io.ylab.intensive.lesson05.eventsourcing.api;

import io.ylab.intensive.lesson05.eventsourcing.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersonApiImpl implements PersonApi {

    private DbClient dbClient;
    private RabbitClient rabbitClient;

    PersonApiImpl(DbClient dbClient, RabbitClient rabbitClient) {
        this.dbClient = dbClient;
        this.rabbitClient = rabbitClient;
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
        Message message = new Message(person, query);
        rabbitClient.sendMessage(message);
    }

    @Override
    public Person findPerson(Long personId) {
        String findPersonQuery = "SELECT * FROM person WHERE person_id = " + personId;
        Person person = null;
        try (ResultSet rs = dbClient.executeQuery(findPersonQuery)) {
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
        String findAllPersonQuery = "SELECT * FROM person";
        List<Person> persons = new ArrayList<>();
        try (ResultSet rs = dbClient.executeQuery(findAllPersonQuery)) {
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
