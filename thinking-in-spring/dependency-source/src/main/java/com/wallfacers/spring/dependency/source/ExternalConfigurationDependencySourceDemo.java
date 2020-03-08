package com.wallfacers.spring.dependency.source;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * 外部化配置
 *
 * @author <a href="wallfacerswu@gamil.com">wallfacers</a>
 * @date 2020/2/27 22:29
 */
@Configuration
@PropertySource(value = "META-INF/default.properties", encoding = "UTF-8")
public class ExternalConfigurationDependencySourceDemo {

    @Value("${person.id:-1}")
    private Integer id;

    @Value("${person.name}")
    private String name;

    @Value("${person.address:杭州}")
    private String address;

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(ExternalConfigurationDependencySourceDemo.class);

        ExternalConfigurationDependencySourceDemo sourceDemo =
                context.getBean(ExternalConfigurationDependencySourceDemo.class);

        System.out.println("sourceDemo.id = " + sourceDemo.id);
        System.out.println("sourceDemo.name = " + sourceDemo.name);
        System.out.println("sourceDemo.address = " + sourceDemo.address);

        context.close();
    }

}
