package com.wallfacers.spring.ioc.overview.dependency.domain;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * 用户实体类
 * 
 * @author <a href="wallfacerswu@gamil.com">wallfacers</a>
 * @date 2020/1/12 15:51
 */
public class Person implements Serializable, BeanNameAware {

    private Long id;

    private String name;

    private Integer age;

    private City city;

    private City[] workCities;

    private List<City> lifeCities;

    private Resource configFileLocation;

    private String beanName;


    public Person() {
    }

    public Person(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Resource getConfigFileLocation() {
        return configFileLocation;
    }

    public void setConfigFileLocation(Resource configFileLocation) {
        this.configFileLocation = configFileLocation;
    }

    public City[] getWorkCities() {
        return workCities;
    }

    public void setWorkCities(City[] workCities) {
        this.workCities = workCities;
    }

    public List<City> getLifeCities() {
        return lifeCities;
    }

    public void setLifeCities(List<City> lifeCities) {
        this.lifeCities = lifeCities;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", city=" + city +
                ", workCities=" + Arrays.toString(workCities) +
                ", lifeCities=" + lifeCities +
                ", configFileLocation=" + configFileLocation +
                '}';
    }

    public static Person createPerson() {
        Person person = new Person();
        person.setId(1L);
        person.setName("tom");
        person.setAge(24);
        return person;
    }

    @PostConstruct
    public void init() {
        System.out.printf("beanName[%s]的Bean执行初始化方法\n", this.beanName);
    }

    @PreDestroy
    public void destroy() {
        System.out.printf("beanName[%s]的Bean执行销毁方法\n", this.beanName);
    }

    @Override
    public void setBeanName(String name) {
        this.beanName = name;
    }
}
