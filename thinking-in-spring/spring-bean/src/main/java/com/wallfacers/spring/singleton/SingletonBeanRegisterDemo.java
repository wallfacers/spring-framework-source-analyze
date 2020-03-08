package com.wallfacers.spring.singleton;

import com.wallfacers.spring.factory.DefaultPersonFactory;
import com.wallfacers.spring.factory.PersonFactory;
import com.wallfacers.spring.initialization.BeanDestroyDemo;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.SingletonBeanRegistry;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Spring注册Bean的两种方式
 * 1、通过BeanDefinition
 * 2、通过SingletonBeanRegistry#registerSingleton方式来注册外部单体对象
 *
 * @author <a href="wallfacerswu@gamil.com">wallfacers</a>
 * @date 2020/1/31 0:17
 */
public class SingletonBeanRegisterDemo {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext();
        context.refresh();

        // 通过SingletonBeanRegistry来注册一个外部单体对象
        SingletonBeanRegistry singletonBeanRegistry = context.getBeanFactory();

        PersonFactory personFactory = new DefaultPersonFactory();
        singletonBeanRegistry.registerSingleton("defaultPersonFactory", personFactory);

        PersonFactory defaultPersonFactory = context.getBean("defaultPersonFactory", PersonFactory.class);

        System.out.println("personFactory == defaultPersonFactory : " + (personFactory == defaultPersonFactory));

        context.close();
    }
}
