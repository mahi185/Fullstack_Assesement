package com.assettrack.repository;
import com.assettrack.model.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
public interface AssetRepository extends JpaRepository<Asset,Long> {}
