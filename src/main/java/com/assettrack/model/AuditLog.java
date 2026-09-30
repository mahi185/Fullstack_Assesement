package com.assettrack.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class AuditLog {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String role;
    private String action;
    private String details;
    private LocalDateTime createdAt;

    public AuditLog(){}
    public AuditLog(String username,String role,String action,String details){
        this.username=username; this.role=role; this.action=action; this.details=details; this.createdAt=LocalDateTime.now();
    }
    public Long getId(){return id;} public String getUsername(){return username;} public String getRole(){return role;}
    public String getAction(){return action;} public String getDetails(){return details;} public LocalDateTime getCreatedAt(){return createdAt;}
}
