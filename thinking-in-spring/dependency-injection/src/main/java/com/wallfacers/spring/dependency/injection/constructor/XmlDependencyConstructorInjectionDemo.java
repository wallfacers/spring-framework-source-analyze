package com.wallfacers.spring.dependency.injection.constructor;

import com.wallfacers.spring.dependency.domain.PersonHolder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

public class XmlDependencyConstructorInjectionDemo {

    public static void main(String[] args) {
        DefaultListableBeanFactory defaultListableBeanFactory = new DefaultListableBeanFactory();
        // 注册BeanDefinitionRegistry
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(defaultListableBeanFactory);
        // 加载xml文件
        xmlBeanDefinitionReader.loadBeanDefinitions("classpath:META-INF/dependency-constructor-injection.xml");

        PersonHolder personHolder = defaultListableBeanFactory.getBean(PersonHolder.class);
        System.out.println(personHolder);
    }
}
