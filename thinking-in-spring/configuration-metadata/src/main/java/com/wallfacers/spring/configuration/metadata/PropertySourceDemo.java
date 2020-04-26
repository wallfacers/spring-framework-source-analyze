package com.wallfacers.spring.configuration.metadata;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

/**
 * 基于Properties加载外部配置，调整优先级
 * Spring 外部化配置默认的优先级
 * System.getProperties 环境路径path
 * System.getEnv 环境变量，key/value
 * 实现目标：让自定义的user.name配置去覆盖系统的环境变量，说白了就是让自定义的环境变量优先
 *
 * @author <a href="wallfacerswu@gamil.com">wallfacers</a>
 * @date 2020/4/5 0:08
 */
// @PropertySource("META-INF/user.properties")
public class PropertySourceDemo {

    @Value("${user.name}")
    private String name;

    public static void main(String[] args) {

        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext();

        // 必须在上下文refresh前，设置自定义的Environment
        /*context.setEnvironment(new StandardEnvironment() {
            @Override
            protected void customizePropertySources(MutablePropertySources propertySources) {
                Resource resource = new DefaultResourceLoader()
                        .getResource("META-INF/user.properties");
                propertySources.addLast(new PropertiesPropertySource(
                        "userProperties", resourceToProperties(resource)));
                super.customizePropertySources(propertySources);
            }
        });*/
        // 方式二：通过addFirst API，不用重新定义StandardEnvironment
        Resource resource = new DefaultResourceLoader()
                .getResource("META-INF/user.properties");
        context.getEnvironment().getPropertySources().addFirst(
                new PropertiesPropertySource(
                "userProperties", resourceToProperties(resource)));

        context.register(PropertySourceDemo.class);
        context.refresh();

        PropertySourceDemo propertySourceDemo = context.getBean(
                "propertySourceDemo", PropertySourceDemo.class);
        System.out.println(propertySourceDemo.name);
        context.close();
    }

    private static Properties resourceToProperties(Resource resource) {

        return resourceToProperties(resource, StandardCharsets.UTF_8.name());
    }

    private static Properties resourceToProperties(EncodedResource encodedResource) {

        return resourceToProperties(encodedResource.getResource(), encodedResource.getEncoding());
    }

    private static Properties resourceToProperties(Resource resource, String encoding) {

        Properties properties = new Properties();
        try (InputStream inputStream = resource.getInputStream()) {
            if (StringUtils.hasText(encoding)) {
                properties.load(new InputStreamReader(inputStream, encoding));
            } else {
                properties.load(inputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }
}
