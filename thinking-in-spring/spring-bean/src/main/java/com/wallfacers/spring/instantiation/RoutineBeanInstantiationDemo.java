package com.wallfacers.spring.instantiation;

import com.wallfacers.spring.factory.PersonFactoryBean;
import com.wallfacers.spring.ioc.overview.dependency.domain.Person;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Bean 实例化的几种方式
 * 1、常规方式
 * 通过构造器(配置元信息：XML, Java注解和Java API)
 * 通过静态工厂方法(配置元信息：XML和Java API)
 * 通过Bean工厂方法(配置元信息: XML,Java注解和Java API)
 * 2、特殊方式
 * 通过ServiceLoaderFactoryBean(配置元信息: XML,Java注解和Java API)
 * 通过AutowireCapableBeanFactory#createBean(java.lang.Class, int, boolean)
 * 通过BeanDefinitionRegistry#registerBeanDefinition(String, BeanDefinition)
 *
 * @author <a href="wallfacerswu@gamil.com">wallfacers</a>
 * @date 2020/1/29 20:28
 */
public class RoutineBeanInstantiationDemo {

    public static void main(String[] args) throws Exception {
        BeanFactory beanFactory = new ClassPathXmlApplicationContext(
                "classpath:META-INF/routine-bean-instantiation.xml");

        Person beanByStaticMethod = beanFactory.getBean("bean-by-static-method", Person.class);
        System.out.println(beanByStaticMethod);

        Person beanByInstantiationMethod = beanFactory.getBean("bean-by-instantiation-method", Person.class);
        System.out.println(beanByInstantiationMethod);

        Person beanByFactoryBean = beanFactory.getBean("bean-by-factory-bean", Person.class);
        System.out.println(beanByFactoryBean);

        System.out.println(beanByStaticMethod == beanByInstantiationMethod);
        System.out.println(beanByStaticMethod == beanByFactoryBean);

        PersonFactoryBean personFactoryBean = beanFactory.getBean("&bean-by-factory-bean", PersonFactoryBean.class);
        System.out.println(personFactoryBean.getObject() == beanByFactoryBean);
    }

}
