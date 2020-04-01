package com.wallfacers.spring.bean.lifecycle;

import com.wallfacers.spring.bean.lifecycle.domain.PersonHolder;
import com.wallfacers.spring.ioc.overview.dependency.domain.Person;
import com.wallfacers.spring.ioc.overview.dependency.domain.SuperPerson;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.DestructionAwareBeanPostProcessor;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.beans.factory.config.SmartInstantiationAwareBeanPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.CommonAnnotationBeanPostProcessor;

import java.util.concurrent.TimeUnit;

/**
 * 其实AOP代理就就是使用这个特殊的后置处理器实现的
 * {@link SmartInstantiationAwareBeanPostProcessor} 可以根据Bean名称或Bean的类型来决定指定的Bean的创建构造器
 *
 * @author <a href="wallfacerswu@gamil.com">wallfacers</a>
 * @date 2020/3/13 0:21
 */
public class BeanLifecycleDemo {

    public static void main(String[] args) throws InterruptedException {

        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        // 与XmlBeanDefinitionReader或PropertiesBeanDefinitionReader不同之处为，这个两个是面向资源的
        // 而注解的Reader，是面向类或注解的
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        beanFactory.addBeanPostProcessor(getMyInstantiationAwareBeanPostProcessor());

        beanFactory.addBeanPostProcessor(getMyDestructionAwareBeanPostProcessor());

        // TODO 注意地方
        // 在AbstractRefreshableWebApplicationContext中通过AnnotationConfigUtils#registerAnnotationConfigProcessors
        // 注册的CommonAnnotationBeanPostProcessor是一个PriorityOrdered优先级的BeanPostProcessor
        // 且CommonAnnotationBeanPostProcessor也优先被注册，而ApplicationContextAwareProcessor
        // AbstractApplicationContext阶段注册的， 所以可能导致上下文的Aware资源得不到注册，这是需要注意的
        // 其实这一块最好的实现方式是通过InitializingBean或者preInstantiateSingletons来进行初始化回调。
        beanFactory.addBeanPostProcessor(new CommonAnnotationBeanPostProcessor());

        // 加载前的
        int beanDefinitionCountBefore = beanFactory.getBeanDefinitionCount();


        String[] locations = {"META-INF/dependency-lookup-context.xml",
                "META-INF/bean-constructor-dependency-injection.xml"};
        reader.loadBeanDefinitions(locations);

        // 注册类，并非一定要@Component注册
        int beanDefinitionCountAfter = beanFactory.getBeanDefinitionCount();

        System.out.println("已加载Bean的数量：" + (beanDefinitionCountAfter - beanDefinitionCountBefore));

        // 剩余单例Bean的创建，以及执行SmartInitializingSingleton#afterSingletonsInstantiated
        beanFactory.preInstantiateSingletons();

        System.out.println(beanFactory.getBean("person"));
        System.out.println(beanFactory.getBean("superPerson"));
        Object personHolder = beanFactory.getBean("personHolder");
        System.out.println(personHolder);

        beanFactory.destroyBean("personHolder", personHolder);

        System.out.println(personHolder);

        // help gc
        personHolder = null;

        beanFactory.destroySingletons();

        System.gc();

        TimeUnit.SECONDS.sleep(5);

        System.gc();
    }

    public static DestructionAwareBeanPostProcessor getMyDestructionAwareBeanPostProcessor() {
        return (bean, beanName) -> {
            System.out.println("自定义Bean的销毁方法方法的执行：" + beanName);
            if ("personHolder".equals(beanName) && PersonHolder.class.equals(bean.getClass())) {
                PersonHolder personHolder = (PersonHolder) bean;
                personHolder.setDescription("The is a description 9");
            }
        };
    }

    public static InstantiationAwareBeanPostProcessor getMyInstantiationAwareBeanPostProcessor() {
        return new InstantiationAwareBeanPostProcessor() {

            /**
             * 在AbstractAutowireCapableBeanFactory#resolveBeforeInstantiation阶段执行
             * （BeanPostProcessor的后置方法还是执行的,但是前置方法是不执行了，且初始化方法也不执行了），
             * 且可以拦截Bean的创建，实现自定义的创建方式
             *
             * @author <a href="wallfacerswu@gamil.com">wallfacers</a>
             * @date 2020/3/14 21:32
             */
            @Override
            public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
                if ("superPerson".equals(beanName) && SuperPerson.class.equals(beanClass)) {
                    return new SuperPerson();
                }
                return null;
            }

            /**
             * 用于拦截Bean populateBean阶段（属性填充阶段）的后续操作，返回true执行，false不执行
             * 其实使用这种方式，也可以手动的对Bean进行属性的填充，需要注意的是这个阶段构造器参数的方式初始化已经执行了
             * 但是字段和方法的初始化还没有执行。这样就可以实现属性的初始化不基于Spring的初始化方式，而是可以手动的初始化
             */
            @Override
            public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
                if ("person".equals(beanName) && Person.class.equals(bean.getClass())) {
                    Person person = (Person) bean;
                    person.setId(System.nanoTime());
                    person.setName("小明");
                    return false;
                }
                return true;
            }

            /**
             * superPerson 和 person Bean是执行不到这里来的，因为被拦截了
             * 在AbstractAutowireCapableBeanFactory#populateBean属性填充阶段，可对Bean的配置列表进行修改
             *
             * AutowiredAnnotationBeanPostProcessor#postProcessProperties的实现，可以注入@value,@autoired,@Inject等注解
             * 标注的字段对应的值，该实现对pvs属性列表没有修改。其依赖DefaultListableBeanFactory#doResolveDependenc方法获取值（Bean和外部配置）
             *
             */
            @Override
            public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName) throws BeansException {


                if ("personHolder".equals(beanName) && PersonHolder.class.equals(bean.getClass())) {
                    final MutablePropertyValues propertyValues;
                    if (pvs instanceof MutablePropertyValues) {
                        propertyValues = (MutablePropertyValues) pvs;
                    }
                    else {
                        propertyValues = new MutablePropertyValues(pvs);
                    }

                    // 对personHolder 实现这样的效果 <property name="number" value="1" />
                    propertyValues.addPropertyValue("number", "1");

                    if (propertyValues.contains("description")) {

                        // 如果添加的值不是Mergeable可以，直接覆盖
                        // propertyValues.addPropertyValue("description", "The is a description 2");

                        // 另外一种覆盖方式，先删除再添加，其实这样要保险点
                        propertyValues.removePropertyValue("description");
                        propertyValues.addPropertyValue("description", "The is a description 2");
                    }
                }
                return pvs;
            }

            /**
             * Bean 的初始化前阶段（populateBean之后），这里指的是初始化方法的执行前也即是AbstractAutowireCapableBeanFactory#invokeInitMethods
             * 方法执行前，这个方法表示@PostConstruct,InitializingBean#afterPropertiesSet，或者自定义的初始化方法
             *
             */
            @Override
            public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
                System.out.println("自定义的前置方法的执行：" + beanName);
                if ("personHolder".equals(beanName) && PersonHolder.class.equals(bean.getClass())) {
                    PersonHolder personHolder = (PersonHolder) bean;
                    personHolder.setDescription("The is a description 3");
                }
                // 根据applyBeanPostProcessorsBeforeInitialization方法的实现，如果某个Bean的前置方法放回null
                // 会拦截后去的Bean的前置方法的执行，后置方法的实现也一样,放回null会拦截后去Bean的后置方法的执行
                return bean;
            }

            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                System.out.println("自定义的后置方法的执行：" + beanName);
                if ("personHolder".equals(beanName) && PersonHolder.class.equals(bean.getClass())) {
                    PersonHolder personHolder = (PersonHolder) bean;
                    personHolder.setDescription("The is a description 7");
                }
                return bean;
            }
        };
    }

}


