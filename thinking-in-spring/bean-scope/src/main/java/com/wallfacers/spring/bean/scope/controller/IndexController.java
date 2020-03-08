package com.wallfacers.spring.bean.scope.controller;

import com.wallfacers.spring.ioc.overview.dependency.domain.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @Autowired
    private Person requestPerson;

    @Autowired
    private DefaultListableBeanFactory beanFactory;

    // jsp获取属性的顺序 page -> request -> session -> application(ServletContext)
    @GetMapping("index.html")
    public String index(Model model) {
        model.addAttribute("person", requestPerson);
        System.out.println(this.requestPerson);
        Person tempRequestPerson = beanFactory.getBean("requestPerson", Person.class);
        System.out.println(tempRequestPerson);
        return "index";
    }
}
