package com.wallfacers.spring.bean.lifecycle;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.PropertiesBeanDefinitionReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;

import java.nio.charset.StandardCharsets;

/**
 * 基于properties文件Bean的元信息配置
 *
 * @author <a href="wallfacerswu@gamil.com">wallfacers</a>
 * @date 2020/3/8 17:38
 */
public class BeanMetadataConfigurationDemo {

    public static void main(String[] args) {

        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        PropertiesBeanDefinitionReader reader = new PropertiesBeanDefinitionReader(beanFactory);

        String location = "META-INF/person.properties";

        // 其本身就是classpath资源，在location中不需要加上classpath
        Resource resource = new ClassPathResource(location);
        // 设置Resource的编码(这里需要设置EncodedResource的encoding属性，而不是charset)
        EncodedResource encodedResource = new EncodedResource(resource, StandardCharsets.UTF_8.toString());

        // 加载properties配置
        int beanNum = reader.loadBeanDefinitions(encodedResource);
        System.out.println("已加载Bean的数量：" + beanNum);

        System.out.println(beanFactory.getBean("person"));
    }

}
