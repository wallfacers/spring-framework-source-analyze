package com.wallfacers.spring.instantiation;

import com.wallfacers.spring.factory.DefaultPersonFactory;
import com.wallfacers.spring.factory.PersonFactory;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ServiceLoader;

import static java.util.ServiceLoader.load;

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
public class SpecificBeanInstantiationDemo {

    public static void main(String[] args) {
        // 由于使用到了AbstractAutowireCapableBeanFactory，就不能用BeanFactory去接收ClassPathXmlApplicationContext
        AbstractApplicationContext context = new ClassPathXmlApplicationContext(
                "classpath:META-INF/specific-bean-instantiation.xml");

        AutowireCapableBeanFactory beanFactory = context.getAutowireCapableBeanFactory();

        // 1、通过ServiceFactoryBean的方式创建Bean
        // 和ServiceLoaderFactoryBean的区别在于ServiceLoaderFactoryBean返回的不是关注的类型的Bean
        // 而是包含了关注的类型的ServiceLoader实例
        // 而ServiceFactoryBean关注用户指定的类型(只返回迭代器中的第一个)
        // ServiceListFactoryBean可以返回列表
        PersonFactory personFactory = beanFactory.getBean(
                "bean-by-service-factory-bean", PersonFactory.class);
        System.out.println(personFactory.createPerson());

        // 2、通过ServiceLoaderFactoryBean的方式创建
        ServiceLoader<PersonFactory> personFactoryServiceLoader = beanFactory.getBean(
                "bean-by-service-loader-factory-bean", ServiceLoader.class);
        iterateServiceLoader(personFactoryServiceLoader);

        // 3、通过AbstractAutowireCapableBeanFactory#createBean的方式创建
        DefaultPersonFactory defaultPersonFactory = beanFactory.createBean(DefaultPersonFactory.class);

        System.out.println(defaultPersonFactory.createPerson());
        // originalServiceLoader();
    }

    public static void originalServiceLoader() {
        // 具备去重能力，如果定义了多个一样的PersonFactory实现，迭代器中只有一个
        iterateServiceLoader(load(PersonFactory.class));
    }

    private static void iterateServiceLoader(ServiceLoader<PersonFactory> personFactories) {
        for (PersonFactory personFactory : personFactories) {
            System.out.println(personFactory.createPerson());
        }
    }
}
