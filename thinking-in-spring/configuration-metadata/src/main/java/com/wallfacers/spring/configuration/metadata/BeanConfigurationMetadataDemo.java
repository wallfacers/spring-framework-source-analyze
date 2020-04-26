package com.wallfacers.spring.configuration.metadata;

import com.wallfacers.spring.ioc.overview.dependency.domain.Person;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.util.ObjectUtils;

/**
 * Bean配置元信息示例
 *
 * @author <a href="wallfacerswu@gamil.com">wallfacers</a>
 * @date 2020/4/1 23:04
 */
public class BeanConfigurationMetadataDemo {

    public static void main(String[] args) {
        AbstractBeanDefinition beanDefinition =
                BeanDefinitionBuilder.genericBeanDefinition(Person.class)
                        .addPropertyValue("name", "wallfacers")
                        .getBeanDefinition();

        // AttributeAccessor 属性上下文存储，与属性元信息不冲突
        beanDefinition.setAttribute("name", "tom");

        // 辅助信息，有些场景下可以用于传值，参与计算
        beanDefinition.setSource(BeanConfigurationMetadataDemo.class);

        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        beanFactory.registerBeanDefinition("person", beanDefinition);

        beanFactory.addBeanPostProcessor(new BeanPostProcessor() {
            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                if (ObjectUtils.nullSafeEquals("person", beanName) && Person.class.equals(bean.getClass())) {
                    BeanDefinition personBeanDefinition = beanFactory.getBeanDefinition("person");
                    // getSource BeanMetadataElement中的接口
                    if (BeanConfigurationMetadataDemo.class.equals(personBeanDefinition.getSource())) {
                        Object name = personBeanDefinition.getAttribute("name");
                        Person person = (Person) bean;
                        person.setName(String.valueOf(name));
                    }
                }
                return bean;
            }
        });

        // 输出：tom
        System.out.println(beanFactory.getBean("person", Person.class).getName());
    }
}
