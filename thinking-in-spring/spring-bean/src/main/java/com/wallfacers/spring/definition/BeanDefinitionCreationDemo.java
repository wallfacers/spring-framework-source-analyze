package com.wallfacers.spring.definition;

import com.wallfacers.spring.ioc.overview.dependency.domain.Person;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.GenericBeanDefinition;

/**
 * 通过 {@link BeanDefinitionBuilder} 和 {@link AbstractBeanDefinition} 的实现类创建Bean
 *
 * @author <a href="wallfacerswu@gamil.com">wallfacers</a>
 * @date 2020/1/29 15:29
 */
public class BeanDefinitionCreationDemo {

    public static void main(String[] args) {
        // 1、通过BeanDefinitionBuilder创建BeanDefinition 可以是GenericBeanDefinition或RootBeanDefinition
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(Person.class);
        // 设置属性
        beanDefinitionBuilder.addPropertyValue("id", 1L)
                .addPropertyValue("name", "tom")
                .addPropertyValue("age", 25);

        // 获取 BeanDefinition 实例 根据genericBeanDefinition方法，其实这里获取到的是GenericBeanDefinition
        AbstractBeanDefinition personBeanDefinition = beanDefinitionBuilder.getBeanDefinition();

        // 2、通过AbstractBeanDefinition的实现列创建
        AbstractBeanDefinition genericBeanDefinition = new GenericBeanDefinition();
        genericBeanDefinition.setBeanClass(Person.class);
        // 设置属性
        MutablePropertyValues mutablePropertyValues = new MutablePropertyValues();
        mutablePropertyValues.add("id", 1L)
                .add("name", "tom")
                .add("age", 25);
        genericBeanDefinition.setPropertyValues(mutablePropertyValues);
    }

}
