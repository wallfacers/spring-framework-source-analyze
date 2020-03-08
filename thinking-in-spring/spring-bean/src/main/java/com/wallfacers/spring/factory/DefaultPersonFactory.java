package com.wallfacers.spring.factory;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * 默认的 {@link PersonFactory} 工厂
 *
 * @author <a href="wallfacerswu@gamil.com">wallfacers</a>
 * @date 2020/1/29 20:44
 */
public class DefaultPersonFactory implements PersonFactory, InitializingBean, DisposableBean {

    @PostConstruct
    public void postConstructMethod() {
        System.out.println("@PostConstruct初始化方法执行。。。");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("InitializingBean#afterPropertiesSet初始化方法被执行。。。");
    }

    public void customInitMethod() {
        System.out.println("自定义的初始化方法被执行。。。");
    }

    @PreDestroy
    public void preDestroy() {
        System.out.println("@preDestroy销毁方法被执行。。。");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("DisposableBean#destory销毁方法被执行");
    }

    public void customDestroyMethod() {
        System.out.println("自定义的销毁方法被执行。。。");
    }

}
