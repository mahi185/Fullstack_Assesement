package com.assettrack.repository;
import com.assettrack.model.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
public interface TransferRepository extends JpaRepository<Transfer,Long> {}
