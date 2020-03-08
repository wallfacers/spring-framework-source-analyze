package com.wallfacers.spring.dependency.injection.method;

import com.wallfacers.spring.dependency.domain.PersonHolder;
import com.wallfacers.spring.ioc.overview.dependency.domain.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import javax.annotation.Resource;

/**
 * 注解注入之方法注入 {@link Autowired},{@link Resource},{@link Bean}都支持方法注入，其实@Inject也是支持的
 */
public class AnnotationDependencyMethodInjectionDemo {

    private PersonHolder personHolder;

    private PersonHolder personHolder2;

    @Autowired
    public void init1(PersonHolder personHolder) {
        this.personHolder = personHolder;
    }

    /**
     * 相对于 {@link Autowired} 先执行
     */
    @Resource
    public void init2(PersonHolder personHolder2) {
        this.personHolder2 = personHolder2;
    }

    @Bean
    public PersonHolder personHolder(Person person) {
        return new PersonHolder(person);
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        // 注册BeanDefinitionRegistry
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(context);
        // 加载xml文件
        xmlBeanDefinitionReader.loadBeanDefinitions("classpath:META-INF/dependency-lookup-context.xml");
        context.register(AnnotationDependencyMethodInjectionDemo.class);
        context.refresh();

        AnnotationDependencyMethodInjectionDemo filedInjectionDemo =
                context.getBean(AnnotationDependencyMethodInjectionDemo.class);

        System.out.println(filedInjectionDemo.personHolder);
        System.out.println(filedInjectionDemo.personHolder2);
        System.out.println(filedInjectionDemo.personHolder == filedInjectionDemo.personHolder2);
        context.close();
    }
}
