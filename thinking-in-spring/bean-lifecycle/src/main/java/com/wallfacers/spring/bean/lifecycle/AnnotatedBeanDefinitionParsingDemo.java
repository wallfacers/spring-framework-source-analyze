package com.wallfacers.spring.bean.lifecycle;

import com.wallfacers.spring.ioc.overview.dependency.domain.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.AnnotatedBeanDefinitionReader;

import static org.springframework.context.annotation.AnnotationConfigUtils.AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME;

/**
 * 注解 BeanDefinition 解析示例
 *
 * @author <a href="wallfacerswu@gamil.com">wallfacers</a>
 * @date 2020/3/8 18:15
 */
public class AnnotatedBeanDefinitionParsingDemo {

    /**
     * 测试发现没有被创建
     * 原因是 Autowired的注入是在AbstractAutowireCapableBeanFactory调用的，在属性填充时
     * 用的是AutowiredAnnotationBeanPostProcessor 后置处理器，但是这个后置处理器是在Application上下文中注册的(AnnotationConfigUtils)
     */
    @Autowired
    public Person person;

    public static void main(String[] args) {

        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 注册一个AutowiredAnnotationBeanPostProcessor
        RootBeanDefinition def = new RootBeanDefinition(AutowiredAnnotationBeanPostProcessor.class);
        beanFactory.registerBeanDefinition(AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME, def);
        // 如果是底层的Ioc比如DefaultListableBeanFactory，需要类似于Context一样，手动创建，才能将BeanFactory注入到其中
        beanFactory.addBeanPostProcessor((BeanPostProcessor) beanFactory.getBean(AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME));

        // Person BeanDefinition
        beanFactory.registerBeanDefinition("person",  createPersonBeanDefinition());

        // 与XmlBeanDefinitionReader或PropertiesBeanDefinitionReader不同之处为，前者面向资源的
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

    private static AbstractBeanDefinition createPersonBeanDefinition() {
        return BeanDefinitionBuilder.genericBeanDefinition(Person.class)
                .addPropertyValue("id", System.currentTimeMillis())
                .addPropertyValue("name", "tom")
                .getBeanDefinition();
    }

}