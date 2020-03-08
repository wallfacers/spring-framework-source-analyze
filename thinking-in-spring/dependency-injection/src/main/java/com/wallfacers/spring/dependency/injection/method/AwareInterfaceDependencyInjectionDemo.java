package com.wallfacers.spring.dependency.injection.method;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.Aware;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.context.*;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 基于{@link Aware}接口回调的依赖注入示例
 * 例如：{@link BeanFactoryAware},{@link ApplicationContextAware},{@link EmbeddedValueResolverAware}
 * {@link EnvironmentAware},{@link ApplicationEventPublisherAware}等等
 */
public class AwareInterfaceDependencyInjectionDemo implements BeanFactoryAware, ApplicationContextAware{

    /**
     * 内建的依赖
     */
    private static BeanFactory beanFactory;

    private static ApplicationContext applicationContext;

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AwareInterfaceDependencyInjectionDemo.class);
        System.out.println(beanFactory == context.getBeanFactory());
        System.out.println(applicationContext == context);
        context.close();
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        AwareInterfaceDependencyInjectionDemo.beanFactory = beanFactory;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        AwareInterfaceDependencyInjectionDemo.applicationContext = applicationContext;

    }
}
