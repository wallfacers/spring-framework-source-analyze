package com.wallfacers.spring.dependency.lookup;

import com.wallfacers.spring.ioc.overview.dependency.domain.Person;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 安全依赖查找
 * 单一类型查找    查找方式                               是否安全
 *                 BeanFactory#getBean                    否
 *                 ObjectFactory#getObject                否
 *                 ObjectProvider#getIfAvailable          是
 * 集合类型查找
 *                 ListableBeanFactory#getBeanOfType      是
 *                 ObjectProvider#stream或iterable        是
 *
 * 层次类型的查找依赖于单一和集合类型的安全性
 * 也即是HierarchicalBeanFactory层次Bean工厂的查找安全性依赖于单一类型和集合类型，AbstractBeanFactory,ListableBeanFactory
 *
 * @author <a href="wallfacerswu@gamil.com">wallfacers</a>
 * @date 2020/2/1 19:17
 */
public class TypeSafetyDependencyDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(TypeSafetyDependencyDemo.class);
        // 判断BeanFactory#getBean方法的安全 不安全
        displayBeanFactoryGetBean(context);
        // 判断ObjectFactory#getObject方法的安全 不安全
        displayObjectFactoryGetObject(context);
        // 判断ObjectProvider#getIfAvalable方法的安全 安全
        displayObjectProviderIsAvailable(context);
        // 判断ListableBeanFactory#getBeansOfType方法的安全 安全
        displayListableBeanFactoryGetBeansOfType(context);
        // 判断ObjectProvider#stream方法的安全 安全
        displayObjectProviderStream(context);
        // 判断ObjectProvider#iterable方法的安全 安全
        displayObjectProviderIterable(context);
        context.close();
    }

    private static void displayBeanFactoryGetBean(AnnotationConfigApplicationContext context) {
        display("displayBeanFactoryGetBean", () -> context.getBean(Person.class));
    }

    private static void displayObjectFactoryGetObject(AnnotationConfigApplicationContext context) {
        // ObjectProvider is ObjectFactory
        ObjectFactory<Person> objectFactory = context.getBeanProvider(Person.class);
        display("displayObjectFactoryGetObject", objectFactory::getObject);
    }

    private static void displayObjectProviderIsAvailable(AnnotationConfigApplicationContext context) {
        ObjectProvider<Person> objectProvider = context.getBeanProvider(Person.class);
        display("displayObjectProviderIsAvailable", objectProvider::getIfAvailable);
    }

    private static void displayListableBeanFactoryGetBeansOfType(AnnotationConfigApplicationContext context) {
        display("displayListableBeanFactoryGetBeansOfType", () -> context.getBeansOfType(Person.class));
    }

    private static void displayObjectProviderStream(AnnotationConfigApplicationContext context) {
        ObjectProvider<Person> objectProvider = context.getBeanProvider(Person.class);
        display("displayObjectProviderStream", () -> objectProvider.stream().forEach(System.out::println));
    }

    private static void displayObjectProviderIterable(AnnotationConfigApplicationContext context) {
        ObjectProvider<Person> objectProvider = context.getBeanProvider(Person.class);
        display("displayObjectProviderIterable", () -> {
            for (Person person : objectProvider) {
                System.out.println(person);
            }
        });
    }

    private static void display(String source, Runnable runnable) {
        System.err.println("source form : " + source);
        try {
            runnable.run();
        } catch (BeansException e) {
            e.printStackTrace();
        }
    }

}
