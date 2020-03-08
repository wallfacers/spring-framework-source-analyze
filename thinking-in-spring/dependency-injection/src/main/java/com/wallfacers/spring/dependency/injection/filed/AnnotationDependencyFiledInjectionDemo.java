package com.wallfacers.spring.dependency.injection.filed;

import com.wallfacers.spring.dependency.domain.PersonHolder;
import com.wallfacers.spring.ioc.overview.dependency.domain.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import javax.annotation.Resource;

/**
 * {@link Autowired}
 * {@link Resource}
 * {@link javax.inject.Inject}
 *
 */
public class AnnotationDependencyFiledInjectionDemo {

    /**
     * {@link Autowired} 不支持静态注入，静态字段会被忽略
     */
    @Autowired
    private /*static*/ PersonHolder personHolder;

    /**
     * {@link Resource} annotation is not supported on static fields
     */
    @Resource
    private /*static*/ PersonHolder personHolder2;

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        // 注册BeanDefinitionRegistry
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(context);
        // 加载xml文件
        xmlBeanDefinitionReader.loadBeanDefinitions("classpath:META-INF/dependency-lookup-context.xml");
        context.register(AnnotationDependencyFiledInjectionDemo.class);
        context.refresh();

        AnnotationDependencyFiledInjectionDemo filedInjectionDemo =
                context.getBean(AnnotationDependencyFiledInjectionDemo.class);

        System.out.println(filedInjectionDemo.personHolder);
        System.out.println(filedInjectionDemo.personHolder2);
        System.out.println(filedInjectionDemo.personHolder == filedInjectionDemo.personHolder2);
        context.close();
    }

    @Bean
    private PersonHolder personHolder(Person person) {
        return  new PersonHolder(person);
    }
}
