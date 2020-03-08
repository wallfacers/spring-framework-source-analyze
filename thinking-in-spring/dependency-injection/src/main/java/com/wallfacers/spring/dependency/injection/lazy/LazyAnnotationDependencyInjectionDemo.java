package com.wallfacers.spring.dependency.injection.lazy;

import com.wallfacers.spring.ioc.overview.dependency.domain.Person;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Collection;

/**
 * {@link ObjectProvider} 或 {@link ObjectFactory} 实现延迟依赖注入
 */
public class LazyAnnotationDependencyInjectionDemo {

    @Autowired
    private ObjectFactory<Collection<Person>> personsObjectFactory;

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        // 注册BeanDefinitionRegistry
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(context);
        // 加载xml文件
        xmlBeanDefinitionReader.loadBeanDefinitions("classpath:META-INF/dependency-lookup-context.xml");
        context.register(LazyAnnotationDependencyInjectionDemo.class);
        context.refresh();

        LazyAnnotationDependencyInjectionDemo filedInjectionDemo =
                context.getBean(LazyAnnotationDependencyInjectionDemo.class);

        System.out.println("filedInjectionDemo.superPerson = " + filedInjectionDemo.personsObjectFactory.getObject());
        System.out.println("============================");
        ObjectProvider<Person> beanProvider = context.getBeanProvider(Person.class);
        System.out.println("filedInjectionDemo.person = " + beanProvider.getObject());
        System.out.println("============================");
        beanProvider.forEach(System.out::println);
        context.close();
    }
}
