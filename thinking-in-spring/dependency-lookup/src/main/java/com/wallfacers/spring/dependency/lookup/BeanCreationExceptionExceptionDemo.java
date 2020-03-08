package com.wallfacers.spring.dependency.lookup;

import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.annotation.PostConstruct;

/**
 * {@link BeanCreationException} 示例
 * Bean创建失败，比如在执行初始化方法时报错，会抛出该异常
 *
 * @author <a href="wallfacerswu@gamil.com">wallfacers</a>
 * @date 2020/2/1 21:40
 */
public class BeanCreationExceptionExceptionDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext();

        context.registerBeanDefinition("errorBean",
                BeanDefinitionBuilder.genericBeanDefinition(InitializeExceptionTest.class).getBeanDefinition());
        context.refresh();
        context.close();
    }

    /**
     * 注解的@PostConsutrct 和 @PreDestroy是在CommonAnnotationBeanPostProcessor阶段处理的
     * 需使用AnnotationConfigApplicationContext上下文，因为该上下文使用AnnotationConfigUtils注册了一些Bean的后置处理器
     * 或BeanFactory后置处理器
     */
    static class InitializeExceptionTest implements InitializingBean {

        @PostConstruct
        public void initPostConstructMethod() throws Exception {
            throw new Exception("PostConstruct注解标注的方法报错");
        }

        @Override
        public void afterPropertiesSet() throws Exception {
            throw new Exception("afterPropertiesSet方法报错");
        }

    }
}
