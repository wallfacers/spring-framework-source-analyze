package com.wallfacers.spring.ioc.overview.dependency.container;

import com.wallfacers.spring.ioc.overview.dependency.annotation.Super;
import com.wallfacers.spring.ioc.overview.dependency.domain.Person;
import com.wallfacers.spring.ioc.overview.dependency.domain.SuperPerson;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextClosedEvent;

import java.util.Map;

/**
 * 注解能力的 {@link AnnotationConfigApplicationContext} 作为IoC容器
 *
 * @author <a href="wallfacerswu@gamil.com">wallfacers</a>
 * @date 2020/1/12 19:47
 */
public class AnnotationApplicationContextIocContainerDemo {

    public static void main(String[] args) {

        //		register(componentClasses);
        //		refresh();
        // 包含了这两步
        // AbstractApplicationContext context = new AnnotationConfigApplicationContext(
        //        AnnotationApplicationContextIocContainerDemo.class);
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(AnnotationApplicationContextIocContainerDemo.class);
        context.refresh();
        context.registerShutdownHook();

        lookupByAnnotationType(context);

        context.addApplicationListener((ApplicationListener<ContextClosedEvent>) event -> System.out.println("hello close"));

        context.close();
    }

    @Bean
    public SuperPerson superPerson() {
        SuperPerson superPerson = new SuperPerson();
        superPerson.setId(1L);
        superPerson.setName("tom");
        superPerson.setAge(24);
        superPerson.setAddress("杭州");
        return superPerson;
    }

    private static void lookupByAnnotationType(BeanFactory beanFactory) {
        if (beanFactory instanceof ListableBeanFactory) {
            ListableBeanFactory listableBeanFactory = (ListableBeanFactory) beanFactory;
            Map<String, Person> beansOfAnnotation = (Map) listableBeanFactory.getBeansWithAnnotation(Super.class);
            System.out.println("通过注解查找到所哟Person对象列表：" + beansOfAnnotation);
        }
    }

}
