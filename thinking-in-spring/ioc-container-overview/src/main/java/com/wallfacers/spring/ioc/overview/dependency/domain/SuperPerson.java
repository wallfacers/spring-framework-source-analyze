package com.wallfacers.spring.ioc.overview.dependency.domain;

import com.wallfacers.spring.ioc.overview.dependency.annotation.Super;

/**
 * 超级用户
 *
 * @author <a href="wallfacerswu@gamil.com">wallfacers</a>
 * @date 2020/1/12 16:25
 */
@Super
public class SuperPerson extends Person{

    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "SuperPerson{" +
                "id=" + super.getId() +
                ", name='" + super.getName() + '\'' +
                ", age=" + super.getAge() +
                ", address='" + address + '\'' +
                '}';
    }
}
