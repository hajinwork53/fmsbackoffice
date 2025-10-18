package com.fms.backoffice.repository;

import com.fms.backoffice.domain.VehicleInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VehicleInfoRepository extends JpaRepository<VehicleInfo, Long> {
    
    Optional<VehicleInfo> findByVin(String vin);
}
