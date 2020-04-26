package com.wallfacers.spring.configuration.yaml;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;

/**
 * 基于 XML定义Yaml资源，让Spring支持yaml
 *
 * @author <a href="wallfacerswu@gamil.com">wallfacers</a>
 * @date 2020/4/5 1:04
 */
public class YamlXmPropertySourceDemo {

    public static void main(String[] args) {

        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext(
                        "META-INF/yaml-properties-source.xml");

        Map<String, Object> yamPropertiesSource = context.getBean(
                "xmlPropertySource", Map.class);

        System.out.println(yamPropertiesSource);

        context.close();
    }

}
