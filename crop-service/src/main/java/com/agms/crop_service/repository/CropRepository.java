package com.agms.crop_service.repository;

import com.agms.crop_service.entity.CropBatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CropRepository extends JpaRepository<CropBatch, Integer> {

}