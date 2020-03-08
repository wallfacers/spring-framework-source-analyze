package com.wallfacers.spring.dependency.injection.setter;

import com.wallfacers.spring.dependency.domain.PersonHolder;
import com.wallfacers.spring.ioc.overview.dependency.domain.Person;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

public class ApiDependencySetterInjection {

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

    /**
     * 创建 {@link PersonHolder} 的 {@link BeanDefinition}
     *
     * @return beanDefinition
     */
    public static BeanDefinition createPersonHolderBeanDefinition() {
        return BeanDefinitionBuilder.genericBeanDefinition(PersonHolder.class)
                .addPropertyReference("person", "person2")
                .getBeanDefinition();
    }
}
