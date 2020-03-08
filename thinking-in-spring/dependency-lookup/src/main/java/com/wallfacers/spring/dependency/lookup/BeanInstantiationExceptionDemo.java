package com.wallfacers.spring.dependency.lookup;

import org.springframework.beans.BeanInstantiationException;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.Serializable;

/**
 * {@link BeanInstantiationException} 示例
 * 创建的Bean不是一个具体类异常
 *
 * @author <a href="wallfacerswu@gamil.com">wallfacers</a>
 * @date 2020/2/1 21:33
 */
public class BeanInstantiationExceptionDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(Serializable.class);
        context.registerBeanDefinition("errorBean", beanDefinitionBuilder.getBeanDefinition());
        context.refresh();
        context.close();
    }

}
