package com.wallfacers.spring.bean.lifecycle;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 测试CommonAnnotationBeanPostProcessor和ApplicationContextAwareProcessor的优先级
 *
 * @author <a href="wallfacerswu@gamil.com">wallfacers</a>
 * @date 2020/3/27 23:11
 */
public class AnnotationBeanPostProcessorPriorityDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AnnotationBeanPostProcessorPriorityDemo.class);

        context.close();
    }

}
