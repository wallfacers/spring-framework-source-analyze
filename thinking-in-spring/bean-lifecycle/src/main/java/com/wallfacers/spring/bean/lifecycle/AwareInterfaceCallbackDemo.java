package com.wallfacers.spring.bean.lifecycle;

import org.springframework.beans.factory.Aware;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Bean的生命周期中：{@link Aware} 接口回调的时机。
 * 以及BeanFactory和ApplicationContext 拥有的Aware是否有区别
 *
 * @author <a href="wallfacerswu@gamil.com">wallfacers</a>
 * @date 2020/3/15 0:24
 */
public class AwareInterfaceCallbackDemo {

    public static void main(String[] args) {
        beanFactoryPossessAwareLookup();
        applicationContextAwareLookup();
    }

    /**
     * BeanFactory 拥有的 Aware接口：
     * BeanNameAware,BeanClassLoaderAware,BeanFactoryAware
     * 执行顺序：查看AbstractAutowireCapableBeanFactory#initializeBean中执行的invokeAwareMethods方法，其实上中定义的顺序
     * 该方法优先于BeanPostProcessor中的前置和后置方法的执行，所以优先于ApplicationContextAwareProcessor的执行
     */
    public static void beanFactoryPossessAwareLookup() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
        String location = "META-INF/bean-constructor-dependency-injection.xml";
        int beanDefinitions = xmlBeanDefinitionReader.loadBeanDefinitions(location);
        System.out.println("已加载Bean的数量：" + beanDefinitions);
        System.out.println(beanFactory.getBean("personHolder"));
    }

    /**
     * ApplicationContext拥有的Aware接口
     * EnvironmentAware,EmbeddedValueResolverAware,ResourceLoaderAware,ApplicationContextEventPublisher
     * ResourceLoaderAware,ApplicationContextAware
     *
     * 其实后4个都是ApplicationContext
     *
     * 这个6个接口是再ApplicationContextAwareProcessor#postProcessBeforeInitialization阶段回调的
     * 该方法虽然和invokeAwareMethods方法都是AbstractAutowireCapableBeanFactory#initializeBean阶段执行的，但是
     * invokeAwareMethods要先执行
     *
     */
    public static void applicationContextAwareLookup() {
        String configLocation = "META-INF/bean-constructor-dependency-injection.xml";
        AbstractApplicationContext context = new ClassPathXmlApplicationContext(configLocation);
        System.out.println(context.getBean("personHolder"));
        context.close();
    }
}
