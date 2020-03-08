package com.wallfacers.spring.bean.scope.config;

import com.wallfacers.spring.ioc.overview.dependency.domain.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.ApplicationScope;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc
public class WebMvcConfiguration {

    @Bean
    // @RequestScope
    // @SessionScope
    @ApplicationScope
    public Person requestPerson() {
        Person person = new Person();
        person.setId(1L);
        person.setName("小明");
        return person;
    }

    @Bean
    public InternalResourceViewResolver resourceViewResolver() {
        InternalResourceViewResolver resourceViewResolver = new InternalResourceViewResolver();
        resourceViewResolver.setPrefix("WEB-INF/");
        resourceViewResolver.setSuffix(".jsp");
        return resourceViewResolver;
    }

}
