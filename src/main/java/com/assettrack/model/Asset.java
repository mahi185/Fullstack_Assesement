package com.assettrack.model;

import jakarta.persistence.*;
@Entity
public class Asset {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String type;
    private String baseName;
    private Integer quantity;
    private String status;

    public Asset() {}
    public Asset(String name,String type,String baseName,Integer quantity,String status){
        this.name=name; this.type=type; this.baseName=baseName; this.quantity=quantity; this.status=status;
    }
    public Long getId(){return id;} public String getName(){return name;} public String getType(){return type;}
    public String getBaseName(){return baseName;} public Integer getQuantity(){return quantity;} public String getStatus(){return status;}
    public void setName(String v){name=v;} public void setType(String v){type=v;} public void setBaseName(String v){baseName=v;}
    public void setQuantity(Integer v){quantity=v;} public void setStatus(String v){status=v;}
}
