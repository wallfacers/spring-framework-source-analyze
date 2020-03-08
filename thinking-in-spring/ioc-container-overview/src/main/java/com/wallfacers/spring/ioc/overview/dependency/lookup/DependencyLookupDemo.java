package com.wallfacers.spring.ioc.overview.dependency.lookup;

import com.wallfacers.spring.ioc.overview.dependency.annotation.Super;
import com.wallfacers.spring.ioc.overview.dependency.domain.Person;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;

public class DependencyLookupDemo {

    public static void main(String[] args) throws Exception {
        BeanFactory beanFactory = new ClassPathXmlApplicationContext(
                "classpath:META-INF/dependency-lookup-context.xml");
        lookupByAnnotationType(beanFactory);
        lookupCollectionByType(beanFactory);
        lookupByType(beanFactory);
        lookupInLazy(beanFactory);
        lookupInRealTime(beanFactory);
    }

    private static void lookupByAnnotationType(BeanFactory beanFactory) {
        if (beanFactory instanceof ListableBeanFactory) {
            ListableBeanFactory listableBeanFactory = (ListableBeanFactory) beanFactory;
            Map<String, Person> beansOfAnnotation = (Map) listableBeanFactory.getBeansWithAnnotation(Super.class);
            System.out.println("通过注解查找到所哟Person对象列表：" + beansOfAnnotation);
        }
    }

    private static void lookupCollectionByType(BeanFactory beanFactory) {

        if (beanFactory instanceof ListableBeanFactory) {
            ListableBeanFactory listableBeanFactory = (ListableBeanFactory) beanFactory;
            Map<String, Person> beansOfType = listableBeanFactory.getBeansOfType(Person.class);
            System.out.println("通过类型查找到所哟Person对象列表：" + beansOfType);
        }
    }

    private static void lookupByType(BeanFactory beanFactory) {
        Person person = beanFactory.getBean(Person.class);
        System.out.println("实时查找：" + person);
    }


    private static void lookupInLazy(BeanFactory beanFactory) throws Exception {
        ObjectFactory<Person> factoryCreatingFactoryBean =
                (ObjectFactory<Person>) beanFactory.getBean("objectFactory");
        Person person = factoryCreatingFactoryBean.getObject();
        System.out.println("延时查找：" + person);
    }

    private static void lookupInRealTime(BeanFactory beanFactory) {
        Person person = (Person) beanFactory.getBean("person");
        System.out.println("实时查找：" + person);
    }
}
