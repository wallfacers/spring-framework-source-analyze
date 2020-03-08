package com.wallfacers.spring.bean.lifecycle;

import com.wallfacers.spring.ioc.overview.dependency.domain.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotatedBeanDefinitionReader;
import org.springframework.context.annotation.Bean;

/**
 * 注解 BeanDefinition 解析示例
 *
 * @author <a href="wallfacerswu@gamil.com">wallfacers</a>
 * @date 2020/3/8 18:15
 */
public class AnnotatedBeanDefinitionParsingDemo {

    @Autowired
    public Person person;

    /**
     * 测试发现没有被创建
     * 原因是 Autowired的注入是在AbstractAutowireCapableBeanFactory调用的，在属性填充时
     * 用的是AutowiredAnnotationBeanPostProcessor 后置处理器，但是这个后置处理器是在Application上下文中注册的(AnnotationConfigUtils)
     */
    @Bean
    public Person person() {
        Person person = new Person();
        person.setId(System.nanoTime());
        return person;
    }

    public static void main(String[] args) {

        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        // 与XmlBeanDefinitionReader或PropertiesBeanDefinitionReader不同之处为，这个两个时面向资源的
        // 而注解的Reader，是面向类或注解的
        AnnotatedBeanDefinitionReader reader = new AnnotatedBeanDefinitionReader(beanFactory);

        // 加载前的
        int beanDefinitionCountBefore = beanFactory.getBeanDefinitionCount();

        // 注册类，并非一定要@Component注册
        reader.register(AnnotatedBeanDefinitionParsingDemo.class);

        int beanDefinitionCountAfter = beanFactory.getBeanDefinitionCount();

        System.out.println("已加载Bean的数量：" + (beanDefinitionCountAfter - beanDefinitionCountBefore));
        AnnotatedBeanDefinitionParsingDemo demo = beanFactory.getBean("annotatedBeanDefinitionParsingDemo", AnnotatedBeanDefinitionParsingDemo.class);
        // BeanName的生成方式AnnotationBeanNameGenerator, Xml和Properties Reader使用的是DefaultBeanNameGenerator
        System.out.println("demo: " + demo);
        System.out.println("demo.person: " + demo.person);

    }

}