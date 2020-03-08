package com.wallfacers.spring.dependency.lookup;

import com.wallfacers.spring.ioc.overview.dependency.domain.Person;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * {@link NoSuchBeanDefinitionException} 示例
 *
 * @author <a href="wallfacerswu@gamil.com">wallfacers</a>
 * @date 2020/2/1 21:21
 */
public class NoSuchBeanDefinitionExceptionDemo {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(NoSuchBeanDefinitionExceptionDemo.class);

        context.getBean(Person.class);

        context.close();
    }

}
