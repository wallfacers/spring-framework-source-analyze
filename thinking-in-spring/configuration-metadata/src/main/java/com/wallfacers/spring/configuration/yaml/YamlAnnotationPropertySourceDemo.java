package com.wallfacers.spring.configuration.yaml;

import org.springframework.context.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 如果PropertySource不生效，尝试在引导类加上{@link Component} 注解，如果存在{@link Bean}方法
 * 或者{@link Import}, {@link ImportResource},{@link ComponentScan}注解等不需要加{@link Component},
 * 具体原因可查看: ConfigurationClassPostProcessor#processConfigBeanDefinitions以及
 *  ConfigurationClassUtils.checkConfigurationClassCandidate实现,因为没有这些注入，Spring认为该类不是一个
 *  候选的的Class，不会对该类上的注解进行进一步递归的解析。
 *
 * @author <a href="wallfacerswu@gamil.com">wallfacers</a>
 * @date 2020/4/5 1:28
 */
@Component
// @Import(Person.class)
@PropertySource(
        name = "annotationPropertySource",
        value = "META-INF/person.yaml",
        factory = YamlPropertySourceFactory.class)
public class YamlAnnotationPropertySourceDemo {

    /*@Bean
    public Person person() {
        return new Person();
    }*/

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(
                        YamlAnnotationPropertySourceDemo.class);

        org.springframework.core.env.PropertySource<?> annotationPropertySource = context.getEnvironment()
                .getPropertySources().get("annotationPropertySource");

        Objects.requireNonNull(annotationPropertySource);

        // 也可用@Value注入进行测试
        System.out.println(annotationPropertySource.getName());
        System.out.println(annotationPropertySource.getProperty("person.id"));
        System.out.println(annotationPropertySource.getProperty("person.age"));
        System.out.println(annotationPropertySource.getProperty("person.name"));
        System.out.println(annotationPropertySource.getProperty("person.city"));

        context.close();
    }

}
