package com.fms.backoffice.repository;

import com.fms.backoffice.domain.VehicleDrivingData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleDrivingDataRepository extends JpaRepository<VehicleDrivingData, Long> {
}
