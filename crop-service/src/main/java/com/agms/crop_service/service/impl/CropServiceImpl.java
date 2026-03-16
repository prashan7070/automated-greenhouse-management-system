package com.agms.crop_service.service.impl;

import com.agms.crop_service.client.ZoneClient;
import com.agms.crop_service.dto.CropDTO;
import com.agms.crop_service.entity.CropBatch;
import com.agms.crop_service.repository.CropRepository;
import com.agms.crop_service.service.CropService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CropServiceImpl implements CropService {

    @Autowired
    private CropRepository repository;

    @Autowired
    private ZoneClient zoneClient;

    @Override
    public CropDTO registerBatch(CropDTO cropDTO, String token) {

        //check if zone exists
        try {
            zoneClient.getZoneById(token, Integer.parseInt(cropDTO.getZoneId()));
        } catch (Exception e) {
            throw new RuntimeException("Invalid Zone ID or Access Denied!");
        }

        // 2. Map DTO to Entity
        CropBatch batch = new CropBatch();
        batch.setCropName(cropDTO.getCropName());
        batch.setZoneId(cropDTO.getZoneId());
        batch.setPlantedDate(LocalDate.now());
        batch.setStatus("SEEDLING"); // default status when registering a new batch

        CropBatch savedBatch = repository.save(batch);

        // 3. Return DTO
        cropDTO.setId(savedBatch.getId());
        cropDTO.setPlantedDate(savedBatch.getPlantedDate());
        cropDTO.setStatus(savedBatch.getStatus());
        return cropDTO;
    }

    @Override
    public List<CropDTO> getAllBatches() {
        return repository.findAll().stream().map(batch -> new CropDTO(
                batch.getId(), batch.getCropName(), batch.getZoneId(),
                batch.getPlantedDate(), batch.getStatus()
        )).collect(Collectors.toList());
    }

    @Override
    public CropDTO updateStatus(Integer id, String status) {
        CropBatch batch = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Batch not found with id: " + id));

        batch.setStatus(status);
        repository.save(batch);

        return new CropDTO(batch.getId(), batch.getCropName(), batch.getZoneId(),
                batch.getPlantedDate(), batch.getStatus());
    }
}