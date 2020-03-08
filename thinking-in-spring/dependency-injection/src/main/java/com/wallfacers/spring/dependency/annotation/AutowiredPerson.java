package com.wallfacers.spring.dependency.annotation;

import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 扩展{@link AutowiredAnnotationBeanPostProcessor} 实现注解注入
 *
 * @author <a href="wallfacerswu@gamil.com">wallfacers</a>
 * @date 2020/2/25 22:15
 */
@Target({ElementType.CONSTRUCTOR, ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface AutowiredPerson {

    /**
     * Declares whether the annotated dependency is required.
     * <p>Defaults to {@code true}.
     */
    boolean required() default true;

}
