package com.wallfacers.spring.dependency.lookup;

import com.wallfacers.spring.ioc.overview.dependency.domain.Person;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

/**
 * 单一类型查找
 * 使用子啊5.1中的BeanFactory提供的延迟查找方法getBeanProvider
 *
 * @author <a href="wallfacerswu@gamil.com">wallfacers</a>
 * @date 2020/2/1 15:16
 */
public class ObjectProviderDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(ObjectProviderDemo.class);
        lookupObjectProvider(context);
        lookupIfAvailable(context);
        lookupStreamOop(context);
        context.close();
    }

    /**
     * Spring 5 对Java8的扩展
     * @param applicationContext 上线文
     */
    public static void lookupIfAvailable(ApplicationContext applicationContext) {
        ObjectProvider<Person> personObjectProvider = applicationContext.getBeanProvider(Person.class);
        Person person = personObjectProvider.getIfAvailable(Person::createPerson);
        System.out.println(person);
    }

    public static void lookupStreamOop(ApplicationContext applicationContext) {
        ObjectProvider<String> stringObjectProvider = applicationContext.getBeanProvider(String.class);
        stringObjectProvider.stream().forEach(System.out::println);
    }

    @Bean
    @Primary
    public String helloWorld() {
        return "Hello, World";
    }

    @Bean
    public String message() {
        return "Message";
    }

    public static void lookupObjectProvider(ApplicationContext applicationContext) {
        // ObjectProvider继承了ObjectFactory作到延迟查找，返回一个ObjectFactory在需要该对象的时候，调用getObject方法
        ObjectProvider<String> helloWorldProvider = applicationContext.getBeanProvider(String.class);

        System.out.println(helloWorldProvider.getObject());
    }
}
