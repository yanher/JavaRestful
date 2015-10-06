package com.springjpa.beans;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;  
import javax.persistence.GeneratedValue;  
import javax.persistence.GenerationType;  
import javax.persistence.Id;  
import javax.persistence.Table; 
@Entity 
@Table(name="firstbean") 
public class FirstBean {
    @Id  
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private int id;
    @Column(name = "type", length = 255)
    private String type;
    @Column(name = "name", length = 45)
    private String name;
    @Column(name = "price", length = 45)
    private String price;
    @Column(name = "position", length = 45)
    private String position;
    @Column(name = "time", length = 45)
    private String time;
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPosition() {
        return position;
    }
    public void setPosition(String position) {
        this.position = position;
    }
    public String getPrice() {
        return price;
    }
    public void setPrice(String price) {
        this.price = price;
    }
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
  
}
