package com.wallfacers.spring.definition;

import com.wallfacers.spring.ioc.overview.dependency.domain.Person;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * Bean的注册几种方式
 * 1、XML的方式
 * 2、注解方式: 比如@Import, @Component, Service, @Bean, @Repository, @Configuration及其派生注解
 * 3、通过API的方式: BeanDefinitionReaderUtils,或BeanDefinitionRegistry
 *
 * @author <a href="wallfacerswu@gamil.com">wallfacers</a>
 * @date 2020/1/29 17:03
 */
@Import(BeanDefinitionRegistryDemo.Config.class)
public class BeanDefinitionRegistryDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext();

        // 注册 Spring配置类
        context.register(BeanDefinitionRegistryDemo.class);
        context.refresh();

        registerPersonBeanDefinition(context, "tom-lina");
        registerPersonBeanDefinition(context);

        // @Import 和 @Component不会重复注册
        System.out.println("Config 配置类：" + context.getBeansOfType(Config.class));
        System.out.println("Person 配置类：" + context.getBeansOfType(Person.class));

        // 关闭上下文
        context.close();
    }

    // 通过BeanDefinitionRegistry API的方式注册Bean
    public static void registerPersonBeanDefinition(BeanDefinitionRegistry registry, String beanName) {

        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(Person.class)
                .addPropertyValue("id", 2)
                .addPropertyValue("name", "tom")
                .addPropertyValue("age", 26)
                .getBeanDefinition();

        // 当Bean名称存在时
        if (StringUtils.hasText(beanName)) {
            registry.registerBeanDefinition(beanName, beanDefinition);
        }
        // 当Bean名称不存在时，通过BeanDefinitionReaderUtils生成Bean的名称
        else {
            BeanDefinitionReaderUtils.registerWithGeneratedName(beanDefinition, registry);
        }

    }

    public static void registerPersonBeanDefinition(BeanDefinitionRegistry registry) {
        registerPersonBeanDefinition(registry, null);
    }

    @Component
    public static class Config {

        @Bean
        public Person person() {
            Person person = new Person();
            person.setAge(26);
            person.setId(2L);
            person.setName("lina");
            return person;
        }
    }
}
