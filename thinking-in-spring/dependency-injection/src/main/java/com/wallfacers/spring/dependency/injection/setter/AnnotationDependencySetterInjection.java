package com.wallfacers.spring.dependency.injection.setter;

import com.wallfacers.spring.dependency.domain.PersonHolder;
import com.wallfacers.spring.ioc.overview.dependency.domain.Person;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

public class AnnotationDependencySetterInjection {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        // 注册BeanDefinitionRegistry
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(context);
        // 加载xml文件
        xmlBeanDefinitionReader.loadBeanDefinitions("classpath:META-INF/dependency-lookup-context.xml");

        context.register(AnnotationDependencySetterInjection.class);
        context.refresh();

        System.out.println(context.getBean("personHolder"));
        context.close();
    }

    /**
     * 这种方式是通过方法参数的方式注入，且是基于方法参数类型查找，不是基于名称，因为方法
     * 参数名称在编译时期后就被编译化为其他参数名称了，只能通过参数类型查找
     *
     * @param person 基于类型查找
     * @return personHolder
     */
    @Bean
    private PersonHolder personHolder(Person person) {
        PersonHolder personHolder = new PersonHolder();
        personHolder.setPerson(person);
        return personHolder;
    }
}
