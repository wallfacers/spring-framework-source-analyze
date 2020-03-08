package com.wallfacers.spring.ioc.overview.dependency.container;

import com.wallfacers.spring.ioc.overview.dependency.annotation.Super;
import com.wallfacers.spring.ioc.overview.dependency.domain.Person;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

import java.util.Map;


/**
 * IOC容器
 * @author <a href="wallfacerswu@gamil.com">wallfacers</a>
 * @date 2020/1/12 19:39
 */
public class BeanFactoryIoCContainerDemo {

    public static void main(String[] args) {

        DefaultListableBeanFactory defaultListableBeanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(defaultListableBeanFactory);

        // 加载配置
        int beanCount = xmlBeanDefinitionReader.loadBeanDefinitions("META-INF/dependency-injection-context.xml");
        System.out.println("Bean 的定义数量：" + beanCount);

        lookupByAnnotationType(defaultListableBeanFactory);
    }

    private static void lookupByAnnotationType(BeanFactory beanFactory) {
        if (beanFactory instanceof ListableBeanFactory) {
            ListableBeanFactory listableBeanFactory = (ListableBeanFactory) beanFactory;
            Map<String, Person> beansOfAnnotation = (Map) listableBeanFactory.getBeansWithAnnotation(Super.class);
            System.out.println("通过注解查找到所哟Person对象列表：" + beansOfAnnotation);
        }
    }

}
