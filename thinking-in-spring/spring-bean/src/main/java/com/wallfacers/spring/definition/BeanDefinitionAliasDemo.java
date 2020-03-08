package com.wallfacers.spring.definition;

import com.wallfacers.spring.ioc.overview.dependency.domain.Person;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 定义Bean的别名
 *
 * @author <a href="wallfacerswu@gamil.com">wallfacers</a>
 * @date 2020/1/29 16:29
 */
public class BeanDefinitionAliasDemo {

    public static void main(String[] args) {
        AbstractApplicationContext context =
                new ClassPathXmlApplicationContext("classpath:META-INF/bean-alias-context.xml");

        Person person = context.getBean("person", Person.class);

        Person tomPerson = context.getBean("tom-person", Person.class);

        System.out.println(person == tomPerson);
    }

}
