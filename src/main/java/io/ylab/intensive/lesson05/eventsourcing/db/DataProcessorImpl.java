package io.ylab.intensive.lesson05.eventsourcing.db;

import io.ylab.intensive.lesson05.eventsourcing.Message;
import io.ylab.intensive.lesson05.eventsourcing.Person;
import io.ylab.intensive.lesson05.eventsourcing.DbClient;
import io.ylab.intensive.lesson05.eventsourcing.RabbitClient;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.TimeoutException;

public class DataProcessorImpl implements DataProcessor {

    private DbClient dbClient;
    private RabbitClient rabbitClient;

    DataProcessorImpl(DbClient dbClient, RabbitClient rabbitClient) {
        this.dbClient = dbClient;
        this.rabbitClient = rabbitClient;
    }

    @Override
    public void deletePerson(Long personId) {
        String deletePersonQuery = "DELETE FROM person WHERE person_id = " + personId + " RETURNING person_id;";
        try (ResultSet rs = dbClient.executeQuery(deletePersonQuery)) {
            if (!rs.next()) {
                System.err.println("Неудачная попытка удаления записи с 'person_id'=" + personId +
                        " - запись с таким 'personId' в БД не найдена.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void savePerson(Person person) {
        String savePersonQuery = String.format("INSERT INTO person VALUES (%d, '%s', '%s', '%s')" +
                        "ON CONFLICT (person_id) DO UPDATE SET first_name='%s', last_name='%s', middle_name='%s'",
                person.getId(), person.getName(), person.getLastName(), person.getMiddleName(),
                person.getName(), person.getLastName(), person.getMiddleName());
        dbClient.executeUpdate(savePersonQuery);
    }

    @Override
    public void start() throws IOException, TimeoutException {
        rabbitClient.listen(this);
    }

    @Override
    public void processing(Message message) {
        switch (message.getQuery()) {
            case SAVE:
                savePerson(message.getPerson());
                break;
            case DELETE:
                deletePerson(message.getPerson().getId());
                break;
            default:
                System.err.println("Неизвестный тип запроса '" + message.getQuery() + "' - запрос не выполнен.");
        }
    }

}
