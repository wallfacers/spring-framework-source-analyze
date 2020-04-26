package com.wallfacers.spring.configuration.xml;

import com.wallfacers.spring.ioc.overview.dependency.domain.Person;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

import java.util.Arrays;

/**
 * 扩展XML，实现自定义的标签创建Bean示例
 *
 * @author <a href="wallfacerswu@gamil.com">wallfacers</a>
 * @date 2020/4/4 17:13
 */
public class ExtensibleXmlAuthorityDemo {

    public static void main(String[] args) {
        DefaultListableBeanFactory beanFactory =
                new DefaultListableBeanFactory();
        XmlBeanDefinitionReader xmlBeanDefinitionReader =
                new XmlBeanDefinitionReader(beanFactory);
        xmlBeanDefinitionReader.loadBeanDefinitions(
                "META-INF/person-extension.xml");
        System.out.println(beanFactory.getBean(Person.class));
    }
}
