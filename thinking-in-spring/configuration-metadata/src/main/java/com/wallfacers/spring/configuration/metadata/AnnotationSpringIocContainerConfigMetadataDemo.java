package com.wallfacers.spring.configuration.metadata;

import com.wallfacers.spring.ioc.overview.dependency.domain.Person;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Component;

/**
 * 基于Java注解装配Spring Ioc容器配置元信息
 * 通过{@link Import} 导入创建的Bean的名称生成与注解{@link Component} 有区别的
 * 在Spring framework 5.2.2 版本中是在 {@link ConfigurationClassPostProcessor}
 * 有个 {@link AnnotationBeanNameGenerator} 实现
 * 在Spring framework 5.2.3 版本被提取到了 {@link FullyQualifiedAnnotationBeanNameGenerator} 类中
 * {@link PropertySource} 支持将读取到properties配置添加到Environment
 *
 * @author <a href="wallfacerswu@gamil.com">wallfacers</a>
 * @date 2020/4/4 15:14
 */
@Import(Person.class)
@PropertySource("META-INF/person-config.properties") // java 8 Repeatable 支持
@PropertySource("META-INF/person-config.properties")
@ImportResource("META-INF/dependency-lookup-context.xml")
public class AnnotationSpringIocContainerConfigMetadataDemo {

    @Bean
    private Person personProperties(@Value("${person.name}") String name, @Value("${person.age}") int age) {
        Person person = new Person();
        person.setName(name);
        person.setAge(age);
        return person;
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AnnotationSpringIocContainerConfigMetadataDemo.class);

        System.out.println("bean 名称 ==============================================");
        context.getBeansOfType(Person.class).keySet().forEach(System.out::println);

        System.out.println("========================================================");
        context.close();
    }

}
