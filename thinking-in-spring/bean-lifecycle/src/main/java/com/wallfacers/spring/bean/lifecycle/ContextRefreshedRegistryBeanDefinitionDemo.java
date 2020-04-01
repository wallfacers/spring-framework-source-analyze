package com.wallfacers.spring.bean.lifecycle;

import com.wallfacers.spring.ioc.overview.dependency.domain.Person;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 上下文启动完成后注册BeanDefinition
 * 测试发现上下文启动完成后，还是可以注册BeanDefinition的，且初始化方法也是可以执行的
 *
 * @author <a href="wallfacerswu@gamil.com">wallfacers</a>
 * @date 2020/3/13 22:32
 */
public class ContextRefreshedRegistryBeanDefinitionDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext configApplicationContext = new AnnotationConfigApplicationContext();
        DefaultListableBeanFactory beanFactory = configApplicationContext.getDefaultListableBeanFactory();
        configApplicationContext.register(ContextRefreshedRegistryBeanDefinitionDemo.class);
        configApplicationContext.refresh();

        beanFactory.registerBeanDefinition("person", createPersonBeanDefinition());

        System.out.println(configApplicationContext.getBean("person"));
        configApplicationContext.close();
    }

    public static BeanDefinition createPersonBeanDefinition() {
        return BeanDefinitionBuilder.genericBeanDefinition(Person.class)
                .addPropertyValue("id", 1L)
                .getBeanDefinition();
    }

}
