package com.wallfacers.spring.dependency.injection.constructor;

import com.wallfacers.spring.dependency.domain.PersonHolder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

public class AutoWringConstructorDependencyConstructorInjectionDemo {

    public static void main(String[] args) {
        DefaultListableBeanFactory defaultListableBeanFactory = new DefaultListableBeanFactory();
        // 注册BeanFactory
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(defaultListableBeanFactory);
        // 加载xml文件
        xmlBeanDefinitionReader.loadBeanDefinitions(
                "classpath:META-INF/auto-wring-constructor-dependency-constructor-injection.xml");

        PersonHolder personHolder = defaultListableBeanFactory.getBean(PersonHolder.class);
        System.out.println(personHolder);
    }
}
