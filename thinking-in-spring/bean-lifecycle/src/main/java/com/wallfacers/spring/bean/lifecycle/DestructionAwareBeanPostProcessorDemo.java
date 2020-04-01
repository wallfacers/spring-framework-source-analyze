package com.wallfacers.spring.bean.lifecycle;

import com.wallfacers.spring.ioc.overview.dependency.domain.Person;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.DestructionAwareBeanPostProcessor;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class DestructionAwareBeanPostProcessorDemo {

    public static void main(String[] args) {

        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        beanFactory.addBeanPostProcessor((DestructionAwareBeanPostProcessor) (bean, beanName) -> {
            if ("tom".equals(beanName) && Person.class.equals(bean.getClass())) {
                System.out.println("postProcessBeforeDestruction : " + beanName);
            }
        });
        beanFactory.registerBeanDefinition("tom", createPersonBeanDefinition());
        System.out.println(beanFactory.getBean("tom"));
        beanFactory.destroySingleton("tom");
    }

    public static BeanDefinition createPersonBeanDefinition() {
        return BeanDefinitionBuilder.genericBeanDefinition(Person.class)
                .addPropertyValue("id", System.nanoTime())
                .getBeanDefinition();
    }

    public static void contextDestructionDemo() {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext();

        DefaultListableBeanFactory defaultListableBeanFactory = context.getDefaultListableBeanFactory();
        defaultListableBeanFactory.addBeanPostProcessor((DestructionAwareBeanPostProcessor) (bean, beanName) -> {
            if ("destructionAwareBeanPostProcessorDemo".equals(beanName)
                    && DestructionAwareBeanPostProcessorDemo.class.equals(bean.getClass())) {
                System.out.println("postProcessBeforeDestruction : " + beanName);
            }
        });

        context.register(DestructionAwareBeanPostProcessorDemo.class);
        context.refresh();
        context.close();
    }
}
