package com.wallfacers.spring.initialization;

import com.wallfacers.spring.factory.DefaultPersonFactory;
import com.wallfacers.spring.factory.PersonFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PreDestroy;

/**
 * 销毁方法的定义三种方式
 * 1、通过{@link PreDestroy} 注解
 * 2、实现{@link DisposableBean} 接口的Destroy方法
 * 3、自定义初始化方法
 *    XML中destroy-method属性或 {@link Bean} 中的destroyMethod属性
 *
 * 执行顺序可查看DisposableBeanAdapter#Destroy方法
 * 其实在AbstractBeanFactory#registerDisposableBeanIfNecessary(doCreateBean阶段执行)阶段被将有销毁方法的Bean添加的disposableBeans集合中
 * Bean的名称作为key,value为DisposableBeanAdapter(对有销毁方法的Bean的包装)的ConcurrentHashMap集合，在Spring上下文的doCLose阶段执行
 * 所有的DisposableBeanAdapter的destroy方法
 *
 * @author <a href="wallfacerswu@gamil.com">wallfacers</a>
 * @date 2020/1/30 23:38
 */
@Configuration
public class BeanDestroyDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(BeanDestroyDemo.class);
        System.out.println("上下文启动完成。。。");
        context.close();
        System.out.println("上下文关闭。。。");
    }

    @Bean(initMethod = "customInitMethod", destroyMethod = "customDestroyMethod")
    public PersonFactory personFactory() {
        return new DefaultPersonFactory();
    }
}
