package com.wallfacers.spring.dependency.domain;

import com.wallfacers.spring.ioc.overview.dependency.domain.Person;

public class PersonHolder {

    private Person person;

    public PersonHolder() {
    }

    public PersonHolder(Person person) {
        this.person = person;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return "PersonHolder{" +
                "person=" + person +
                '}';
    }
}
