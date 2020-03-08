package com.wallfacers.spring.dependency.lookup;

import com.wallfacers.spring.ioc.overview.dependency.domain.Person;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.HierarchicalBeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 层次查找，在Spring中并没有直接提供层次查找能力需要使用{@link BeanFactoryUtils}来解决或手动实现
 *
 * @author <a href="wallfacerswu@gamil.com">wallfacers</a>
 * @date 2020/2/1 16:06
 */
public class HierarchicalDependencyDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

        // 注册上下文的配置类，配置其实可以不必标注@Configuration注解
        context.register(HierarchicalDependencyDemo.class);
        // 创建父上下文
        HierarchicalBeanFactory parentBeanFactory = createParentBeanFactory();

        // 设置父上下文
        ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
        beanFactory.setParentBeanFactory(parentBeanFactory);

        context.refresh();

        lookupByContainsLocalBean(parentBeanFactory, "person");
        lookupByContainsLocalBean(beanFactory, "person");

        displayContainsBean(parentBeanFactory, "person");
        displayContainsBean(beanFactory, "person");

        ListableBeanFactory listableBeanFactory = (ListableBeanFactory) parentBeanFactory;
        displayUtilsContainsBean(listableBeanFactory);
        displayUtilsContainsBean(beanFactory);

        context.close();
    }
    private static void displayUtilsContainsBean(ListableBeanFactory beanFactory) {
        // beanOfTypeIncludingAncestors可以实现递归层次查找
        Person person = BeanFactoryUtils.beanOfTypeIncludingAncestors(beanFactory, Person.class);

        System.out.println(person);
    }

    private static void displayContainsBean(HierarchicalBeanFactory beanFactory, String beanName) {
        System.out.printf("BeanFactory[%s]是否包含 Bean[%s]: %s\n",
                beanFactory, beanName, containsBean(beanFactory, beanName));
    }

    private static boolean containsBean(HierarchicalBeanFactory beanFactory, String beanName) {
        BeanFactory parentBeanFactory = beanFactory.getParentBeanFactory();
        if (parentBeanFactory instanceof HierarchicalBeanFactory) {
            HierarchicalBeanFactory hierarchicalBeanFactory = (HierarchicalBeanFactory) parentBeanFactory;
            return containsBean(hierarchicalBeanFactory, beanName);
        }
        return beanFactory.containsLocalBean(beanName);
    }

    private static void lookupByContainsLocalBean(HierarchicalBeanFactory beanFactory, String beanName) {
        System.out.printf("BeanFactory[%s]是否包含 Bean[%s]: %s\n",
                beanFactory, beanName, beanFactory.containsLocalBean("person"));
    }

    public static HierarchicalBeanFactory createParentBeanFactory() {
        DefaultListableBeanFactory defaultListableBeanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(defaultListableBeanFactory);
        xmlBeanDefinitionReader.loadBeanDefinitions("classpath:META-INF\\dependency-lookup-context.xml");
        return defaultListableBeanFactory;
    }

}
