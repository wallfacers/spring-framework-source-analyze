package com.wallfacers.spring.configuration.xml;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * 指定标签与解析器对应的关系
 *
 * @author <a href="wallfacerswu@gamil.com">wallfacers</a>
 * @date 2020/4/4 17:09
 */
public class PersonNamespaceHandler extends NamespaceHandlerSupport {

    @Override
    public void init() {
        registerBeanDefinitionParser("person", new PersonBeanDefinitionParser());
    }
}
