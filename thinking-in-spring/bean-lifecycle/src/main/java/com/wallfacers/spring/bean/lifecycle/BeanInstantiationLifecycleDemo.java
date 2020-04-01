package com.wallfacers.spring.bean.lifecycle;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.factory.config.SmartInstantiationAwareBeanPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.InstantiationStrategy;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.ParameterNameDiscoverer;

/**
 * Bean的实例化阶段，也即是{@link BeanWrapper}
 * 非特殊情况下的Bean的创建方式: {@link InstantiationStrategy}
 * 结论一：Spring在运行时期虽然可以通过{@link ParameterNameDiscoverer}得到构造器参数，但是其还是使用了类型注入
 *        Java 8之前，接口上的参数名称是获取不到的。
 *        而在ConstructorResolver#autowireConstructor阶段解析出来的构造器参数名称
 *        用于获取名称对于配置的值，也即是constructor-arg配置名称对应的值或引用
 * 结论二：非特殊情况，也即是使用{@link InstantiationStrategy}去获取默认的构造器创建Bean，一般特殊情况都是指定了构造器
 *
 * 结论三：如果有一个空参的构造器和一个单参数的构造器，访问限定符一样时优先参数多的构造器，如果访问限定符不一样
 *         范围大的优先匹配，具体查看AutowireUtils.sortConstructors排序方式
 *
 * 指定某个Bean的构造器可以通过 {@link SmartInstantiationAwareBeanPostProcessor} 和 RootBeanDefinition#getPreferredConstructors
 *
 *
 * @author <a href="wallfacerswu@gamil.com">wallfacers</a>
 * @date 2020/3/14 15:00
 */
public class BeanInstantiationLifecycleDemo {

    public static void main(String[] args) {

        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        XmlBeanDefinitionReader xmlReader = new XmlBeanDefinitionReader(beanFactory);
        String[] locations =  {"META-INF/dependency-lookup-context.xml", "META-INF/bean-constructor-dependency-injection.xml"};
        int beanDefinitionCount = xmlReader.loadBeanDefinitions(locations);
        System.out.println("加载Bean的数量：" + beanDefinitionCount);

        System.out.println("personHolder: " + beanFactory.getBean("personHolder"));
    }

}
