package com.wallfacers.spring.dependency.source;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

/**
 * 游离单例对象是否能够使用AbstractBeanFactory#destroyBean进行销毁方法的调用
 *
 * @author <a href="wallfacerswu@gamil.com">wallfacers</a>
 * @date 2020/3/31 23:51
 */
@Component
public class FloatSingletonDestroyDemo {

    public static class FloatSingleton implements DisposableBean {
        @Override
        public void destroy() throws Exception {
            System.out.println("FloatSingleton#destroy");
        }
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(FloatSingletonDestroyDemo.class);
        DefaultListableBeanFactory beanFactory = context.getDefaultListableBeanFactory();
        beanFactory.registerSingleton("floatSingleton", new FloatSingleton());
        Object floatSingleton = context.getBean("floatSingleton");
        System.out.println(floatSingleton);
        beanFactory.destroyBean("beanFactory", floatSingleton);
        context.close();
    }

}
