package com.assettrack.controller;
import com.assettrack.model.Asset;
import com.assettrack.repository.AssetRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/api/assets")
@CrossOrigin(origins="http://localhost:5173")
public class AssetController {
    private final AssetRepository repo;
    public AssetController(AssetRepository repo){this.repo=repo;}
    @GetMapping public List<Asset> all(){return repo.findAll();}
    @PostMapping public Asset add(@RequestBody Asset a){return repo.save(a);}
}
