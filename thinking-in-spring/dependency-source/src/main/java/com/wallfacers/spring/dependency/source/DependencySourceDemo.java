package com.wallfacers.spring.dependency.source;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.ResourceLoader;

import javax.annotation.PostConstruct;

/**
 * 依赖来源示例
 * 依赖注入比依赖查找多一个来源，那就是Spring内建的依赖
 * 因为通过注入（比如@Autowired注入）可以注入在AbstractApplicationContext#refresh阶段中的prepareBeanFactory阶段添加的
 * 添加到了DefaultListableBeanFactory的resolvableDependencies线程安全的Map容器中
 * 而在doResolveDependency阶段中的findAutowireCandidates阶段用到
 * 所以依赖查找（getBean）的阶段是不能得到内建的依赖的，比如不能通过getBean获取BeanFactory,ResourceLoader,ApplicationContext.
 * ApplicationEventPublisher等，其实在Spring上下文的prepareBeanFactory阶段存储的后三个对象都是ApplicationContext
 * 通过依赖注入（比如手动的是使用@Autowired注解）是可以注入的，因为其发生在BeanPostProcessor
 *
 * @author <a href="wallfacerswu@gamil.com">wallfacers</a>
 * @date 2020/2/26 22:14
 */
public class DependencySourceDemo {

    @Autowired
    private BeanFactory beanFactory;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    /**
     * 初始化方法的执行晚于Bean的填充，或依赖注入
     */
    @PostConstruct
    public void initPostConstruct() {
        getBean(BeanFactory.class);
        getBean(ApplicationContext.class);
        getBean(ResourceLoader.class);
        getBean(ApplicationEventPublisher.class);
    }

    public <T> T getBean(Class<T> type) {
        try {
            return this.applicationContext.getBean(type);
        } catch (NoSuchBeanDefinitionException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DependencySourceDemo.class);

        DependencySourceDemo dependencySourceDemo = context.getBean(DependencySourceDemo.class);

        System.out.println("beanFactory == applicationContext : " +
                (dependencySourceDemo.beanFactory == dependencySourceDemo.applicationContext));

        System.out.println("beanFactory == applicationContext : " +
                (dependencySourceDemo.beanFactory == dependencySourceDemo.applicationContext.getAutowireCapableBeanFactory()));

        System.out.println("applicationContext == resourceLoader : " +
                (dependencySourceDemo.applicationContext == dependencySourceDemo.resourceLoader));

        System.out.println("resourceLoader == applicationEventPublisher : " +
                (dependencySourceDemo.resourceLoader == dependencySourceDemo.applicationEventPublisher));


        context.close();
    }

}
