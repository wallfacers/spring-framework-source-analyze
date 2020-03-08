package com.wallfacers.spring.bean.scope;

import com.wallfacers.spring.ioc.overview.dependency.domain.Person;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

import static com.wallfacers.spring.bean.scope.ThreadLocalScope.THREAD_LOCAL_NAME;

/**
 * 基于Api的方式对自定义的Scope进行注册
 *
 * @author <a href="wallfacerswu@gamil.com">wallfacers</a>
 * @date 2020/3/6 23:51
 */
public class ThreadLocalScopeApiRegisterDemo {

    @Bean
    @Scope(THREAD_LOCAL_NAME)
    public Person person() {
        return createPerson();
    }

    private static Person createPerson() {
        Person person = new Person();
        person.setId(System.nanoTime());
        return person;
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(ThreadLocalScopeApiRegisterDemo.class);

        DefaultListableBeanFactory beanFactory = context.getDefaultListableBeanFactory();
        beanFactory.registerScope(THREAD_LOCAL_NAME, new ThreadLocalScope());
        context.refresh();

        scopeBeansByLookup(beanFactory);
        context.close();
    }

    private static void scopeBeansByLookup(DefaultListableBeanFactory beanFactory) {
        for (int i = 0; i < 3; i++) {
            Thread thread = new Thread(() -> {
                Person person = beanFactory.getBean("person", Person.class);
                System.out.printf("当前线程id[%s], 当前Person: %s\n", Thread.currentThread().getId(), person);
            });
            thread.start();
        }
    }
}
