package com.wallfacers.spring.bean.lifecycle.domain;

import com.wallfacers.spring.ioc.overview.dependency.domain.Person;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class PersonHolder implements BeanNameAware,
        BeanClassLoaderAware, BeanFactoryAware,
        EnvironmentAware, InitializingBean,
        SmartInitializingSingleton, DisposableBean{

    private Integer number;

    private String description;

    private Person person;

    private String beanName;

    private ClassLoader classLoader;

    private BeanFactory beanFactory;

    private Environment environment;

    public PersonHolder() {
    }

    public PersonHolder(Person person) {
        this.person = person;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void setBeanName(String name) {
        this.beanName = name;
    }

    @Override
    public String toString() {
        return "PersonHolder{" +
                "number=" + number +
                ", description='" + description + '\'' +
                ", person=" + person +
                ", beanName='" + beanName + '\'' +
                ", classLoader=" + classLoader +
                ", beanFactory=" + beanFactory +
                '}';
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    /**
     * 在InitDestroyAnnotationBeanPostProcessor#postProcessBeforeInitialization接口中回调的
     * 需要CommonAnnotationBeanPostProcessor的支持
     */
    @PostConstruct
    public void postConstruct() {
        this.description = "The is a description 4";
        System.out.println("postConstruct: The is a description 4");
    }

    /**
     * AbstractAutowireCapableBeanFactory#invokeInitMethods方法中执行，在postProcessBeforeInitialization之后
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        this.description = "The is a description 5";
        System.out.println("afterPropertiesSet: The is a description 5");
    }

    /**
     * AbstractAutowireCapableBeanFactory#invokeInitMethods方法中执行
     *
     * 执行顺序
     * PostConstruct ->  InitializingBean -> init-method属性
     */
    public void init() {
        this.description = "The is a description 6";
        System.out.println("init: The is a description 6");
    }

    @Override
    public void afterSingletonsInstantiated() {
        this.description = "The is a description 8";
        System.out.println("afterSingletonsInstantiated: The is a description 8");
    }

    @PreDestroy
    public void preDestroy() {
        this.description = "The is a description 10";
        System.out.println("preDestroy: The is a description 10");
    }


    @Override
    public void destroy() throws Exception {
        this.description = "The is a description 11";
        System.out.println("destroy: The is a description 11");
    }

    public void doDestroy() {
        this.description = "The is a description 12";
        System.out.println("doDestroy: The is a description 12");
    }

    // 销毁方法的执行顺序：preDestroy -> destroy -> doDestroy
    // 具体实现可查看DisposableBeanAdapter#destory


    @Override
    public void finalize() throws Throwable {
        System.out.println("执行PersonHolder销毁方法");
    }
}