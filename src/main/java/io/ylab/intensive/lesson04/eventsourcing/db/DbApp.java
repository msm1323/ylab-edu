package io.ylab.intensive.lesson04.eventsourcing.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.*;
import io.ylab.intensive.lesson04.DbUtil;
import io.ylab.intensive.lesson04.RabbitMQUtil;
import io.ylab.intensive.lesson04.eventsourcing.Message;
import io.ylab.intensive.lesson04.eventsourcing.Person;

public class DbApp {

    private static String QUEUE_NAME = "apiAppQueue";
//    private static String EXCHANGE_NAME = "exc";

    public static void main(String[] args) throws Exception {
        DataSource dataSource = initDb();
//        DataSource dataSource = DbUtil.buildDataSource();
        ConnectionFactory connectionFactory = initMQ();
        ObjectMapper objectMapper = new ObjectMapper();

        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {
//            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
//            channel.queueBind(QUEUE_NAME, EXCHANGE_NAME,"*");

            while (!Thread.currentThread().isInterrupted()) {
                GetResponse response = channel.basicGet(QUEUE_NAME, true);
                if (response != null) {
                    Message message = objectMapper.readValue(response.getBody(), Message.class);
                    switch (message.getQuery()) {
                        case SAVE:
                            savePerson(dataSource, message.getPerson());
                            break;
                        case DELETE:
                            deletePerson(dataSource, message.getPerson().getId());
                            break;
                        default:
                            System.err.println("Неизвестный тип запроса '" + message.getQuery() + "' - запрос не выполнен.");
                    }
                }

            }
        }
    }

    private static void deletePerson(DataSource dataSource, Long personId) {
        String deletePersonQuery = "DELETE FROM person WHERE person_id = ? RETURNING person_id";
        try (java.sql.Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deletePersonQuery)) {
            preparedStatement.setLong(1, personId);
            ResultSet rs = preparedStatement.executeQuery();
            if (!rs.next()) {
                System.err.println("Неудачная попытка удаления записи с 'person_id'=" + personId +
                        " - запись с таким 'personId' в БД не найдена.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void savePerson(DataSource dataSource, Person person) {
        String savePersonQuery = "INSERT INTO person VALUES (?,?,?,?)" +
                "ON CONFLICT (person_id) DO UPDATE SET first_name=?, last_name=?, middle_name=?";
        try (java.sql.Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(savePersonQuery)) {
            preparedStatement.setLong(1, person.getId());
            preparedStatement.setString(2, person.getName());
            preparedStatement.setString(3, person.getLastName());
            preparedStatement.setString(4, person.getMiddleName());
            preparedStatement.setString(5, person.getName());
            preparedStatement.setString(6, person.getLastName());
            preparedStatement.setString(7, person.getMiddleName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private static ConnectionFactory initMQ() throws Exception {
        return RabbitMQUtil.buildConnectionFactory();
    }

    private static DataSource initDb() throws SQLException {
        String ddl = ""
                + "drop table if exists person;"
                + "create table if not exists person (\n"
                + "person_id bigint primary key,\n"
                + "first_name varchar,\n"
                + "last_name varchar,\n"
                + "middle_name varchar\n"
                + ")";
        DataSource dataSource = DbUtil.buildDataSource();
        DbUtil.applyDdl(ddl, dataSource);
        return dataSource;
    }
}
