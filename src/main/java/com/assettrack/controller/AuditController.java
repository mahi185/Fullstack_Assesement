package com.assettrack.controller;
import com.assettrack.model.AuditLog;
import com.assettrack.repository.AuditLogRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/api/audit-logs")
@CrossOrigin(origins="http://localhost:5173")
public class AuditController {
    private final AuditLogRepository repo;
    public AuditController(AuditLogRepository repo){this.repo=repo;}
    @GetMapping public List<AuditLog> all(){return repo.findAll();}
}
