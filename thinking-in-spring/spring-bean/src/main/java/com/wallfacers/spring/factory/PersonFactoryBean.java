package com.wallfacers.spring.factory;

import com.wallfacers.spring.ioc.overview.dependency.domain.Person;
import org.springframework.beans.factory.FactoryBean;

/**
 * 通过 {@link FactoryBean} 的方式创建Bean
 *
 * @author <a href="wallfacerswu@gamil.com">wallfacers</a>
 * @date 2020/1/29 20:49
 */
public class PersonFactoryBean implements FactoryBean<Person> {

    @Override
    public Person getObject() throws Exception {
        return Person.createPerson();
    }

    @Override
    public Class<?> getObjectType() {
        return Person.class;
    }
}
