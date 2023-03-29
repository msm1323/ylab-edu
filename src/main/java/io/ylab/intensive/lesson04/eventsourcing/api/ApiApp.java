package io.ylab.intensive.lesson04.eventsourcing.api;

import com.rabbitmq.client.ConnectionFactory;
import io.ylab.intensive.lesson04.DbUtil;
import io.ylab.intensive.lesson04.RabbitMQUtil;
import io.ylab.intensive.lesson04.eventsourcing.Person;

import javax.sql.DataSource;

public class ApiApp {
    public static void main(String[] args) throws Exception {
        ConnectionFactory connectionFactory = initMQ();
        DataSource dataSource = DbUtil.buildDataSource();
        PersonApiImpl personApi = new PersonApiImpl(dataSource, connectionFactory);

        personApi.savePerson(1L, "Clive", "Staples", "Lewis");
        personApi.savePerson(2L, "John", "Jackson", "J");
        personApi.savePerson(3L, "Terence", "David", "Pratchett");

        personApi.savePerson(456876536L, "Aaaa", "CCCCC", "BBBB");
        personApi.savePerson(999999999L, "A", "O", "U");

        personApi.savePerson(3L, "Terence", "David John", "Pratchett");

        personApi.deletePerson(999999999L);
        personApi.deletePerson(77L);

//    Thread.sleep(3000);
        Person person_1 = personApi.findPerson(1L);
        System.out.println(person_1.getName());

        System.out.println(personApi.findAll());

    }

    private static ConnectionFactory initMQ() throws Exception {
        return RabbitMQUtil.buildConnectionFactory();
    }
}
