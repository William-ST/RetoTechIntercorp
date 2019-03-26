package com.sulca.retotechintercorp.data.entity;

/**
 * Created by everis on 25/03/19.
 */
public class UserEntity {

    private String id;
    private String name;
    private String lastname;
    private int age;
    private String dateborn;

    public UserEntity() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getDateborn() {
        return dateborn;
    }

    public void setDateborn(String dateborn) {
        this.dateborn = dateborn;
    }
}
