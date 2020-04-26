package com.wallfacers.spring.configuration.yaml;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;

import java.io.IOException;
import java.util.Objects;

/**
 * 基于{@link org.springframework.context.annotation.PropertySource} 注解扩展读取yaml实现
 *
 * @author <a href="wallfacerswu@gamil.com">wallfacers</a>
 * @date 2020/4/5 1:16
 */
public class YamlPropertySourceFactory implements PropertySourceFactory {

    @Override
    public PropertySource<?> createPropertySource(
            String name, EncodedResource resource) throws IOException {

        YamlPropertiesFactoryBean factoryBean = new YamlPropertiesFactoryBean();

        factoryBean.setResources(resource.getResource());

        return new PropertiesPropertySource(name, Objects.requireNonNull(factoryBean.getObject()));
    }
}
