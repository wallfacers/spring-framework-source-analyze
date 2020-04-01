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
     * BUG:
     * 这里只是注入了XML配置的几个Person的原因：
     * 可查看DefaultListableBeanFactory#findAutowireCandidates实现，这是因为在候选Bean查找的过程中
     * 如果结果集不为空就直接放回了，不会执行对自引用的Bean判断添加，这里的引用表示自己注入自己
     * 或者该Bean是由当前类通过@Bean创建的，我们知道@Bean的创建其实就是工厂方法
     * 出现这种情况是因为使用了Xml和注解上下文混搭的情况。或者在该类外部也定义候选类型的Bean
     * 具体可查看https://github.com/spring-projects/spring-framework/issues/23934
     * 将@Bean创建方法编程一个静态的，就会打破这种自引用性问题
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
