package com.wallfacers.spring.dependency.injection.qualifier;

import com.wallfacers.spring.dependency.annotation.PersonGroup;
import com.wallfacers.spring.dependency.domain.PersonHolder;
import com.wallfacers.spring.ioc.overview.dependency.domain.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Collection;

/**
 * {@link Qualifier} 注解注入，作用域（所有的作用域），以及分组能力
 * SpringCloudd的@Loadbanlance也是基于在注解上标注@Qualifier加上该注解分组能力
 * 将@Loadbanlance注解在@RestTemplate上，实现负载均衡能力
 */
public class QualifierAnnotationDependencyInjectionDemo {

    @Autowired
    private Person superPerson;

    @Autowired
    @PersonGroup("person2")
    private Person person;

    /**
     * 获取XML文件的Person列表
     */
    @Autowired
    private Collection<Person> allPersons;

    /**
     * 标注了Qualifier注解的Person Bean
     * PersonGroup 注解也是 Qualifier
     */
    @Autowired
    @Qualifier
    private Collection<Person> qualifierPerson;

    /**
     * 标注了PersonGroup注解的Person Bean
     */
    @Autowired
    @PersonGroup
    private Collection<Person> personGroupPersons;

    @Bean
    @Qualifier
    public Person person11() {
        return createPerson(11);
    }

    @Bean
    @Qualifier
    public Person person12() {
        return createPerson(12);
    }

    @Bean
    @PersonGroup
    public Person person13() {
        return createPerson(13);
    }

    @Bean
    @PersonGroup
    public Person person14() {
        return createPerson(14);
    }

    @Bean
    public Person person15() {
        return createPerson(15);
    }

    @Bean
    public PersonHolder personHolder(@Qualifier("person2") Person person2) {
        return new PersonHolder(person2);
    }

    private static Person createPerson(long id) {
        Person person = new Person();
        person.setId(id);
        return person;
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        // 注册BeanDefinitionRegistry
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(context);
        // 加载xml文件
        xmlBeanDefinitionReader.loadBeanDefinitions("classpath:META-INF/dependency-lookup-context.xml");
        context.register(QualifierAnnotationDependencyInjectionDemo.class);
        context.refresh();

        QualifierAnnotationDependencyInjectionDemo filedInjectionDemo =
                context.getBean(QualifierAnnotationDependencyInjectionDemo.class);

        System.out.println("filedInjectionDemo.superPerson =" + filedInjectionDemo.superPerson);
        System.out.println("filedInjectionDemo.person =" + filedInjectionDemo.person);
        System.out.println("personHolder =" + context.getBean("personHolder"));
        System.out.println("filedInjectionDemo.allPersons =" + filedInjectionDemo.allPersons);
        System.out.println("filedInjectionDemo.qualifierPerson =" + filedInjectionDemo.qualifierPerson);
        System.out.println("filedInjectionDemo.personGroupPersons =" + filedInjectionDemo.personGroupPersons);

        context.close();
    }
}
