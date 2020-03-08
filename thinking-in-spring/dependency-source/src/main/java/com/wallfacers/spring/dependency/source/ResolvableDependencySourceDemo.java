package com.wallfacers.spring.dependency.source;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 添加依赖注入的来源
 *
 * @author <a href="wallfacerswu@gamil.com">wallfacers</a>
 * @date 2020/2/27 22:14
 */
public class ResolvableDependencySourceDemo {

    @Autowired
    private String helloWorld;

    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

        // 在上下文没有refresh前不要使用getAutowireCapableBeanFactory方法去获取上下文，因为它会断言上下文是否激活
        // AutowireCapableBeanFactory beanFactory = context.getAutowireCapableBeanFactory();

        // 用getBeanFactory或getDefaultListableBeanFactory方法都可以
        // ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
        // DefaultListableBeanFactory beanFactory = context.getDefaultListableBeanFactory();

        // 基于BeanFactoryPostProcessor的方式进行注册，因为生命周期的原因
        context.addBeanFactoryPostProcessor(beanFactory -> {
            beanFactory.registerResolvableDependency(String.class, "Hello, World");
        });

        context.register(ResolvableDependencySourceDemo.class);
        context.refresh();

        ResolvableDependencySourceDemo sourceDemo = context.getBean(ResolvableDependencySourceDemo.class);

        System.out.println(sourceDemo.helloWorld);

        context.close();

    }

}
