package com.assettrack.controller;
import com.assettrack.model.Transfer;
import com.assettrack.model.AuditLog;
import com.assettrack.repository.TransferRepository;
import com.assettrack.repository.AuditLogRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/api/transfers")
@CrossOrigin(origins="http://localhost:5173")
public class TransferController {
    private final TransferRepository repo; private final AuditLogRepository audit;
    public TransferController(TransferRepository repo, AuditLogRepository audit){this.repo=repo;this.audit=audit;}
    @GetMapping public List<Transfer> all(){return repo.findAll();}
    @PostMapping public Transfer add(@RequestBody Transfer t){
        Transfer saved=repo.save(t);
        audit.save(new AuditLog("admin","ADMIN","TRANSFER_CREATED","Transfer "+saved.getId()+" created"));
        return saved;
    }
}
