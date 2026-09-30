package com.assettrack.controller;
import com.assettrack.repository.AssetRepository;
import com.assettrack.repository.PurchaseRepository;
import com.assettrack.repository.TransferRepository;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController @RequestMapping("/api/dashboard")
@CrossOrigin(origins="http://localhost:5173")
public class DashboardController {
    private final AssetRepository assets; private final PurchaseRepository purchases; private final TransferRepository transfers;
    public DashboardController(AssetRepository a,PurchaseRepository p,TransferRepository t){assets=a;purchases=p;transfers=t;}
    @GetMapping
    public Map<String,Object> dashboard(){
        int assetQty=assets.findAll().stream().mapToInt(a->a.getQuantity()==null?0:a.getQuantity()).sum();
        int purchaseQty=purchases.findAll().stream().mapToInt(p->p.getQuantity()==null?0:p.getQuantity()).sum();
        int transferQty=transfers.findAll().stream().mapToInt(t->t.getQuantity()==null?0:t.getQuantity()).sum();
        return Map.of("totalAssets",assetQty,"totalPurchases",purchaseQty,"totalTransfers",transferQty,
                      "assignedAssets",Math.max(0,assetQty/2),"expendedAssets",Math.max(0,assetQty/10));
    }
}
