package com.wallfacers.spring.dependency.injection.constructor;

import com.wallfacers.spring.dependency.domain.PersonHolder;
import com.wallfacers.spring.ioc.overview.dependency.domain.Person;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

public class AnnotationDependencyConstructorInjectionDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        // 注册BeanDefinitionRegistry
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(context);
        // 加载xml文件
        xmlBeanDefinitionReader.loadBeanDefinitions("classpath:META-INF/dependency-lookup-context.xml");

        context.register(AnnotationDependencyConstructorInjectionDemo.class);
        context.refresh();

        System.out.println(context.getBean("personHolder"));
        context.close();
    }

    @Bean
    private PersonHolder personHolder(Person person) {
        return new PersonHolder(person);
    }
}
