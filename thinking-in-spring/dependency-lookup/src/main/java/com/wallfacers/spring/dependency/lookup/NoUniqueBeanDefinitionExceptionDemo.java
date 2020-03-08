package com.wallfacers.spring.dependency.lookup;

import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * {@link NoUniqueBeanDefinitionException} 示例
 *
 * @author <a href="wallfacerswu@gamil.com">wallfacers</a>
 * @date 2020/2/1 21:27
 */
public class NoUniqueBeanDefinitionExceptionDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(NoUniqueBeanDefinitionExceptionDemo.class);

        try {
            context.getBean(String.class);
        } catch (NoUniqueBeanDefinitionException e) {
            System.out.printf("Bean的个数[%d] Bean的类型[%s] 错误原因[%s]\n",
                    e.getNumberOfBeansFound(), String.class.getName(), e.getMessage());
        }

        context.close();
    }

    @Bean
    public String bean1() {
        return "1";
    }

    @Bean
    public String bean2() {
        return "2";
    }

    @Bean
    public String bean3() {
        return "3";
    }

}
