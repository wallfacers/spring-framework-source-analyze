package com.wallfacers.spring.bean.lifecycle;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.stereotype.Component;

/**
 * 上下文关闭GC
 *
 * @author <a href="wallfacerswu@gamil.com">wallfacers</a>
 * @date 2020/3/28 14:15
 */
@Component
public class ContextCloseGcDemo {

    @Configuration
    public static class Person  {

        @Override
        public void finalize() throws Throwable {
            System.out.println("person gc");
        }
    }

    public static void main(String[] args) {
        AbstractApplicationContext context = new AnnotationConfigApplicationContext(ContextCloseGcDemo.class);
        Person person = context.getBean(Person.class);
        System.out.println(person);
        context.close();
        // help GC
        person = null;
        System.gc();
    }
}
