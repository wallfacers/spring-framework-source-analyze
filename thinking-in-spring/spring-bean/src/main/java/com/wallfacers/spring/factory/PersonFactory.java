package com.wallfacers.spring.factory;

import com.wallfacers.spring.ioc.overview.dependency.domain.Person;

public interface PersonFactory {

    default Person createPerson() {
        return Person.createPerson();
    }
}
