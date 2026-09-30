package com.assettrack.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Transfer {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String fromBase;
    private String toBase;
    private String equipmentType;
    private Integer quantity;
    private String status;
    private LocalDateTime createdAt;

    public Transfer(){createdAt=LocalDateTime.now(); status="COMPLETED";}
    public Transfer(String fromBase,String toBase,String equipmentType,Integer quantity){
        this.fromBase=fromBase; this.toBase=toBase; this.equipmentType=equipmentType; this.quantity=quantity; this.status="COMPLETED"; this.createdAt=LocalDateTime.now();
    }
    public Long getId(){return id;} public String getFromBase(){return fromBase;} public String getToBase(){return toBase;}
    public String getEquipmentType(){return equipmentType;} public Integer getQuantity(){return quantity;} public String getStatus(){return status;} public LocalDateTime getCreatedAt(){return createdAt;}
}
