package com.example.footballclub.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="user")
public class User {


    private String userId;
    @Column
    private String openid;
    @Column
    private String name;
    @Column
    private Integer sex;
    @Column
    private Integer role;
    @Column
    private Integer age;
    @Column
    private Integer footballPosition;
    @Column
    private Date createTime;

    @Id
    @GenericGenerator(name="idGenerator",strategy = "uuid")//hibernate中生成32位uuid的注解
    @GeneratedValue(generator = "idGenerator")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getFootballPosition() {
        return footballPosition;
    }

    public void setFootballPosition(Integer footballPosition) {
        this.footballPosition = footballPosition;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
