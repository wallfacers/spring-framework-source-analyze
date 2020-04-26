package com.wallfacers.spring.configuration.metadata;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.PropertiesBeanDefinitionReader;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;

import java.nio.charset.StandardCharsets;

/**
 * PropertiesBeanDefinitionReader示例
 *
 * @author <a href="wallfacerswu@gamil.com">wallfacers</a>
 * @date 2020/4/2 21:46
 */
public class PropertiesBeanDefinitionReaderDemo {

    public static void main(String[] args) {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        PropertiesBeanDefinitionReader beanDefinitionReader = new PropertiesBeanDefinitionReader(beanFactory);

        // 解决Properties文件中文乱码问题
        Resource resource = new DefaultResourceLoader().getResource("META-INF/person-config.properties");

        EncodedResource encodedResource = new EncodedResource(resource, StandardCharsets.UTF_8.name());

        int beanDefinitions = beanDefinitionReader.loadBeanDefinitions(encodedResource);

        System.out.println("加载到的BeanDefinition数量：" + beanDefinitions);

        System.out.println(beanFactory.getBean("person"));

        System.out.println(beanFactory.getBean("super.person"));
    }

}
