package io.ylab.intensive.lesson04.eventsourcing;

public class Message {

    private Person person;
    private Query query;

    Message(){}

    public Message(Person person, Query query) {
        this.person = person;
        this.query = query;
    }

    public Person getPerson() {
        return person;
    }

    public Query getQuery() {
        return query;
    }
}
