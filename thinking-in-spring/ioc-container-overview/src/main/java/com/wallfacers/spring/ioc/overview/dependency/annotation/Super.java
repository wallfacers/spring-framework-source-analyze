package com.wallfacers.spring.ioc.overview.dependency.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * super注解
 *
 * @author <a href="wallfacerswu@gamil.com">wallfacers</a>
 * @date 2020/1/12 16:34
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Super {
}
