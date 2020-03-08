package com.wallfacers.spring.dependency.injection.resolution;

import com.wallfacers.spring.dependency.annotation.AutowiredPerson;
import com.wallfacers.spring.dependency.annotation.MyAutowired;
import com.wallfacers.spring.ioc.overview.dependency.domain.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.*;

import javax.inject.Inject;
import java.util.Map;
import java.util.Optional;

/**
 * 依赖注入的源码分析
 */
@Configuration
public class AnnotationDependencyInjectionResolutionDemo {

    /**
     * 延迟注入
     * eager = false
     * 查看 {@link ContextAnnotationAutowireCandidateResolver}的getLazyResolutionProxyIfNecessary实现
     */
    @Autowired
    @Lazy
    private Person lazyPerson;

    /**
     * 依赖注入
     * DependencyDescriptor元信息：
     * declaringClass = com.wallfacers.spring.dependency.injection.resolution.AnnotationDependencyInjectionResolutionDemo
     * containingClass = com.wallfacers.spring.dependency.injection.resolution.AnnotationDependencyInjectionResolutionDemo
     * fieldName = person
     * required = true
     * eager = true
     * field = person
     * <p>
     * 期待注入 = superPerson
     */
    @Autowired
    private Person person;

    /**
     * 依赖注入集合类型
     */
    @Autowired
    private Map<String, Person> persons;

    /**
     * {@link Inject} 注入
     */
    @Inject
    private Person injectPerson;

    /**
     * Optional依赖注入
     */
    @Autowired
    private Optional<Person> optionalPerson;

    /**
     * 使用自定义的{@link MyAutowired} 实现字段注入
     */
    @MyAutowired
    private Person myAutoWiredPerson;

    /**
     * 基于覆盖原有的{@link AutowiredAnnotationBeanPostProcessor}实现方式
     */
    /*@Bean(AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME)
    public static AutowiredAnnotationBeanPostProcessor beanPostProcessor() {
        AutowiredAnnotationBeanPostProcessor beanPostProcessor = new AutowiredAnnotationBeanPostProcessor();
        beanPostProcessor.setAutowiredAnnotationTypes(getAutowiredAnnotationTypes());
        return beanPostProcessor;
    }

    public static Set<Class<? extends Annotation>> getAutowiredAnnotationTypes() {
        Set<Class<? extends Annotation>> autowiredAnnotationTypes =  new LinkedHashSet<>(4);
        autowiredAnnotationTypes.add(Autowired.class);
        autowiredAnnotationTypes.add(Value.class);
        try {
            autowiredAnnotationTypes.add((Class<? extends Annotation>)
                    ClassUtils.forName("javax.inject.Inject", AutowiredAnnotationBeanPostProcessor.class.getClassLoader()));
        }
        catch (ClassNotFoundException ex) {
            // JSR-330 API not available - simply skip.
        }
        autowiredAnnotationTypes.add(AutowiredPerson.class);
        return autowiredAnnotationTypes;
    }*/

    /**
     * 多个实现，不覆盖{@link AnnotationConfigUtils} 注册的 {@link AutowiredAnnotationBeanPostProcessor}
     * 调整其执行顺序，order值越小，越优先执行
     *
     * @author <a href="wallfacerswu@gamil.com">wallfacers</a>
     * @date 2020/2/25 22:32
     */
    @Bean
    // @Order(Ordered.LOWEST_PRECEDENCE - 3) 这样设置order是可以的
    public static AutowiredAnnotationBeanPostProcessor beanPostProcessor() {
        AutowiredAnnotationBeanPostProcessor beanPostProcessor = new AutowiredAnnotationBeanPostProcessor();
        // 这样设置order,同样也可以
        // beanPostProcessor.setOrder(Ordered.LOWEST_PRECEDENCE - 3);
        beanPostProcessor.setAutowiredAnnotationType(AutowiredPerson.class);

        // 测试发现不设置，order也能达到目的
        return beanPostProcessor;
    }

    /**
     * 使用自定义的{@link AutowiredPerson} 实现字段注入
     */
    @AutowiredPerson
    private Person autowiredPerson;

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        // 注册BeanDefinitionRegistry
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(context);
        // 加载xml文件
        xmlBeanDefinitionReader.loadBeanDefinitions("classpath:META-INF/dependency-lookup-context.xml");
        context.register(AnnotationDependencyInjectionResolutionDemo.class);
        context.refresh();

        AnnotationDependencyInjectionResolutionDemo resolutionInjectionDemo =
                context.getBean(AnnotationDependencyInjectionResolutionDemo.class);

        System.out.println("person: " + resolutionInjectionDemo.person);
        System.out.println("injectPerson: " + resolutionInjectionDemo.injectPerson);
        System.out.println("persons: " + resolutionInjectionDemo.persons);
        System.out.println("optionalPerson: " + resolutionInjectionDemo.optionalPerson.orElse(null));
        System.out.println("lazyPerson: " + resolutionInjectionDemo.lazyPerson);
        System.out.println("myAutoWiredPerson: " + resolutionInjectionDemo.myAutoWiredPerson);
        System.out.println("autowiredPerson: " + resolutionInjectionDemo.autowiredPerson);
        context.close();
    }
}
