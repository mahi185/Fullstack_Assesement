package com.assettrack.model;

import jakarta.persistence.*;
@Entity
@Table(name="bases")
public class Base {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String name;
    public Base() {}
    public Base(String name) { this.name=name; }
    public Long getId(){return id;}
    public String getName(){return name;}
    public void setName(String name){this.name=name;}
}
