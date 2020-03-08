package com.wallfacers.spring.bean.scope;

import com.wallfacers.spring.ioc.overview.dependency.domain.Person;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

import java.util.Map;
import java.util.Set;

/**
 * Bean的作用域, 单例Bend和原型Bean
 * 结论一：依赖查找或依赖注入，如果Bean的Scope是Singleton无论执行多少次，返回的都是通过个Bean，如果是Prototype每次执行
 *         都会新生成一个Bean
 *
 * 结论二：注入一个集合, 如Map， 这时会将单例和原型都注入进去，原型Bean是新创建的
 *
 * 结论三：原型Bean不具备单例Bean一样的完整的生命周期，原型Bean的初始化方法会执行，但是销毁方法没有执行
 *
 *         问题：官方给出的解决方案是，通过BeanPostProcessor的postProcessAfterInitialization方法去执行销毁操作
 *         不推荐使用这种方式，这种方式是在Bean初始化完成之后执行，这样会导致如果我这个原型Bean返回出去，刚好在使用中
 *         这时又对一些资源进行了销毁操作，可能会出些一些问题。
 *
 *         推荐使用销毁原型Bean的方式，如果这原型Bean所属与某个类，那么所属的这个类中的destroy方法中执行该Bean的
 *         销毁工作
 *
 * @author <a href="wallfacerswu@gamil.com">wallfacers</a>
 * @date 2020/2/28 23:48
 */
public class BeanScopeSingletonPrototypeDemo implements DisposableBean {

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public static Person singletonPerson() {
        return new Person(System.nanoTime());
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public static Person prototypePerson() {
        return new Person(System.nanoTime());
    }

    @Autowired
    @Qualifier("singletonPerson")
    private Person singletonPerson;

    @Autowired
    @Qualifier("singletonPerson")
    private Person singletonPerson2;

    @Autowired
    @Qualifier("prototypePerson")
    private Person prototypePerson;

    @Autowired
    @Qualifier("prototypePerson")
    private Person prototypePerson2;

    @Autowired
    @Qualifier("prototypePerson")
    private Person prototypePerson3;

    @Autowired
    private Map<String, Person> persons;


    // Resolvable Dependency 内建的依赖
    @Autowired
    private DefaultListableBeanFactory beanFactory;

    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(BeanScopeSingletonPrototypeDemo.class);

        DefaultListableBeanFactory beanFactory = context.getDefaultListableBeanFactory();

        // 不推荐使用BeanPostProcessor的后置处理方法对原型Bean进行销毁
        beanFactory.addBeanPostProcessor(new BeanPostProcessor() {
            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                if ("prototypePerson".equals(beanName) && bean instanceof Person) {
                    Person prototypePerson = (Person) bean;
                    prototypePerson.destroy();
                }
                return bean;
            }
        });

        context.refresh();

        scopeBeansByDependencyLookup(beanFactory);
        scopeBeansByDependencyInject(beanFactory);

        context.close();
    }

    private static void scopeBeansByDependencyLookup(DefaultListableBeanFactory beanFactory) {
        for (int i = 0; i < 3; i++) {
            Person singletonPerson = beanFactory.getBean("singletonPerson", Person.class);
            System.out.println("scopeBeansByBeanType->singletonPerson = " + singletonPerson);

            Person prototypePerson = beanFactory.getBean("prototypePerson", Person.class);
            System.out.println("scopeBeansByBeanType->prototypePerson = " + prototypePerson);
        }
    }

    private static void scopeBeansByDependencyInject(DefaultListableBeanFactory beanFactory) {
        BeanScopeSingletonPrototypeDemo singletonPrototypeDemo = beanFactory.getBean(BeanScopeSingletonPrototypeDemo.class);
        System.out.println("scopeBeansByDependencyInject->singletonPerson = " + singletonPrototypeDemo.singletonPerson);
        System.out.println("scopeBeansByDependencyInject->singletonPerson = " + singletonPrototypeDemo.singletonPerson2);

        System.out.println("scopeBeansByDependencyInject->prototypePerson = " + singletonPrototypeDemo.prototypePerson);
        System.out.println("scopeBeansByDependencyInject->prototypePerson = " + singletonPrototypeDemo.prototypePerson2);
        System.out.println("scopeBeansByDependencyInject->prototypePerson = " + singletonPrototypeDemo.prototypePerson3);

        System.out.println("scopeBeansByDependencyInject->persons = " + singletonPrototypeDemo.persons);

    }

    @Override
    public void destroy() throws Exception {
        System.out.println("BeanScopeSingletonPrototypeDemo#destroy销毁方法开始执行");
        this.prototypePerson.destroy();
        this.prototypePerson2.destroy();
        this.prototypePerson3.destroy();

        this.persons.forEach((beanName, bean) -> {
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);
            if (beanDefinition.isPrototype()) {
                bean.destroy();
            }
        });

        System.out.println("BeanScopeSingletonPrototypeDemo#destroy销毁方法执行结束");
    }
}
