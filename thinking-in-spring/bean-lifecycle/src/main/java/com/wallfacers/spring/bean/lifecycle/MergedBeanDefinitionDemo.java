package com.wallfacers.spring.bean.lifecycle;

import org.springframework.beans.factory.xml.BeanDefinitionParserDelegate;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 有parent属性的GenericBeanDefinition合并过程，
 *     Xml方式创建的GenericBeanDefinition，具体查看 {@link BeanDefinitionParserDelegate#createBeanDefinition
 * 最终被合并为RootBeanDefinition，其实这是属性的合并
 *
 * @author <a href="wallfacerswu@gamil.com">wallfacers</a>
 * @date 2020/3/12 22:18
 */
public class MergedBeanDefinitionDemo {

    public static void main(String[] args) {

        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("META-INF/dependency-lookup-context.xml");
        // XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
        // int beanDefinitionCount = xmlBeanDefinitionReader.loadBeanDefinitions("META-INF/dependency-lookup-context.xml");

        // 一般的BeanDefinition其没有parentName就直接转换成RootBeanDefinition
        System.out.println(context.getBean("person"));
        // 如果是存在parentName属性，会递归的寻找然后对属性进行合并
        // 查找过程:
        // 当前BeanDefinition -> ParentBeanDefinition -> ParentBeanDefinition 找到最后一个parent为止
        // 属性的合并，最上层的parent bean的属性会下一层次的bean覆盖
        // 具体查看 AbstractBeanFactory#getMergedBeanDefinition方法
        System.out.println(context.getBean("superPerson"));
        context.close();
    }

}
