package com.assettrack.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Purchase {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String baseName;
    private String equipmentType;
    private Integer quantity;
    private LocalDateTime createdAt;
    private String supplier;

    public Purchase(){createdAt=LocalDateTime.now();}
    public Purchase(String baseName,String equipmentType,Integer quantity,String supplier){
        this.baseName=baseName; this.equipmentType=equipmentType; this.quantity=quantity; this.supplier=supplier; this.createdAt=LocalDateTime.now();
    }
    public Long getId(){return id;} public String getBaseName(){return baseName;} public String getEquipmentType(){return equipmentType;}
    public Integer getQuantity(){return quantity;} public LocalDateTime getCreatedAt(){return createdAt;} public String getSupplier(){return supplier;}
}
