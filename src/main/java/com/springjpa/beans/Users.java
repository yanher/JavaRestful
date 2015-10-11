package com.springjpa.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
@Entity 
@Table(name="users") 
@XmlRootElement(name = "users")
public class Users {
    @Id
    @GeneratedValue
    @Column(name = "name", length = 30)
    private String name;
    @Column(name = "pwd", length = 11)
    private Integer pwd;

    @XmlElement(name="name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @XmlElement(name="pwd")
    public Integer getPwd() {
        return pwd;
    }

    public void setPwd(Integer pwd) {
        this.pwd = pwd;
    }
}
