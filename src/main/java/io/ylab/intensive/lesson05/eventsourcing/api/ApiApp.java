package io.ylab.intensive.lesson05.eventsourcing.api;

import io.ylab.intensive.lesson05.eventsourcing.Person;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ApiApp {

    public static void main(String[] args) throws Exception {
        // Тут пишем создание PersonApi, запуск и демонстрацию работы
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);
        applicationContext.start();
        PersonApi personApi = applicationContext.getBean(PersonApi.class);

        personApi.savePerson(1L, "Clive", "Staples", "Lewis");
        personApi.savePerson(2L, "John", "Jackson", "J");
        personApi.savePerson(3L, "Terence", "David", "Pratchett");

        personApi.savePerson(456876536L, "Aaaa", "CCCCC", "BBBB");
        personApi.savePerson(999999999L, "A", "O", "U");

        personApi.savePerson(3L, "Terence", "David John", "Pratchett");

        personApi.deletePerson(999999999L);
        personApi.deletePerson(77L);

        Thread.sleep(3000);
        Person person_1 = personApi.findPerson(1L);
        System.out.println(person_1.getName());

        System.out.println(personApi.findAll());
    }

}
