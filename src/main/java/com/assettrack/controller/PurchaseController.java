package com.assettrack.controller;
import com.assettrack.model.Purchase;
import com.assettrack.model.AuditLog;
import com.assettrack.repository.PurchaseRepository;
import com.assettrack.repository.AuditLogRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/api/purchases")
@CrossOrigin(origins="http://localhost:5173")
public class PurchaseController {
    private final PurchaseRepository repo; private final AuditLogRepository audit;
    public PurchaseController(PurchaseRepository repo, AuditLogRepository audit){this.repo=repo;this.audit=audit;}
    @GetMapping public List<Purchase> all(){return repo.findAll();}
    @PostMapping public Purchase add(@RequestBody Purchase p){
        Purchase saved=repo.save(p);
        audit.save(new AuditLog("admin","ADMIN","PURCHASE_CREATED","Purchase "+saved.getId()+" created"));
        return saved;
    }
}
