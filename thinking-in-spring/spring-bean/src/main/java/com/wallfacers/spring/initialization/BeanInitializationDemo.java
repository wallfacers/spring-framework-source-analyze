package com.wallfacers.spring.initialization;

import com.wallfacers.spring.factory.DefaultPersonFactory;
import com.wallfacers.spring.factory.PersonFactory;
import org.springframework.beans.factory.annotation.InitDestroyAnnotationBeanPostProcessor;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import javax.annotation.PostConstruct;

/**
 * 初始化方法的定义三种方式
 * 1、通过{@link PostConstruct}注解（发生在{@link InitDestroyAnnotationBeanPostProcessor}的before阶段，也是属性填充后）
 * 2、通过实现InitializingBean#afterPropertiesSet方法（属性填充后）
 * 3、自定义初始化方法
 *    定义方式: XML的init-method属性，{@link Bean}注解的initMethod属性，通过AbstractBeanDefinition#setInitMethodName设置
 *
 * 延迟初始化指的是在Spring启动的refresh阶段不对Bean创建，在refresh阶段之后如果有getBean操作，才进行Bean的创建和执行初始化
 * 初始化方法的执行是在Bean的属性填充之后，这里的延迟初始化也即是Lazy表示的是延迟创建Bean，而不是Bean创建好了，延迟执行初始化方法
 * 因为Bean的创建和执行初始化接连发生的，在完成依赖查找时，执行Bean的初始化。
 *
 * 三者顺序可查看：AbstractAutowireCapableBeanFactory#initializeBean方法
 * @author <a href="wallfacerswu@gamil.com">wallfacers</a>
 * @date 2020/1/29 22:49
 */
@Configuration
public class BeanInitializationDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext();
        context.register(BeanInitializationDemo.class);
        context.refresh();
        System.out.println("上下文启动完成");
        PersonFactory personFactory = context.getBean("personFactory", PersonFactory.class);
        System.out.println(personFactory);
        context.close();
    }

    /**
     * {@link Lazy} 注解针对于Bean的延迟创建，也标志着初始化方法延迟执行
     * 这是因为Bean创建完成后属性填充完马上执行初始化方法
     */
    @Bean(initMethod = "customInitMethod")
    @Lazy
    public PersonFactory personFactory() {
        return new DefaultPersonFactory();
    }
}
