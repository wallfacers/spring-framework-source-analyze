package com.wallfacers.spring.dependency.injection.constructor;

import com.wallfacers.spring.dependency.domain.PersonHolder;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ApiDependencyConstructorInjectionDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        // 注册BeanDefinitionRegistry
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(context);
        // 加载xml文件
        xmlBeanDefinitionReader.loadBeanDefinitions("classpath:META-INF/dependency-lookup-context.xml");

        context.registerBeanDefinition("personHolder", createPersonHolderBeanDefinition());
        context.refresh();

        System.out.println(context.getBean("personHolder"));
        context.close();
    }

    public static BeanDefinition createPersonHolderBeanDefinition() {
        return BeanDefinitionBuilder.genericBeanDefinition(PersonHolder.class)
                .addConstructorArgReference("person2")
                .getBeanDefinition();
    }
}
