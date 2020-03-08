package com.wallfacers.spring.ioc.overview.dependency.injection;

import com.wallfacers.spring.ioc.overview.dependency.repository.PersonRepository;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.env.Environment;

public class DependencyInjectionDemo {

    public static void main(String[] args) throws Exception {
        BeanFactory beanFactory = new ClassPathXmlApplicationContext(
                "classpath:META-INF/dependency-injection-context.xml");

        // 自定义的Bean
        PersonRepository personRepository = beanFactory.getBean(PersonRepository.class);

        System.out.println(personRepository.getPersonList());

        // 是否同源？
        // 依赖注入(内建的依赖)
        System.out.println(personRepository.getBeanFactory());
        // System.out.println(beanFactory == personRepository.getBeanFactory());

        // 依赖查找(错误)
        // System.out.println(beanFactory.getBean(BeanFactory.class));

        ObjectFactory<ApplicationContext> personObjectFactory = personRepository.getObjectFactory();

        System.out.println(personObjectFactory.getObject());

        // 容器内建的Bean
        Environment environment = beanFactory.getBean(Environment.class);
        System.out.println("获取Environment对象Bean" + environment);
    }
}
