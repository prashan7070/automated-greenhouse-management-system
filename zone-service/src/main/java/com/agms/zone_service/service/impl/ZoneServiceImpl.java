package com.agms.zone_service.service.impl;

import com.agms.zone_service.dto.ZoneDTO;
import com.agms.zone_service.entity.Zone;
import com.agms.zone_service.repository.ZoneRepository;
import com.agms.zone_service.service.ZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ZoneServiceImpl implements ZoneService {

    @Autowired
    private ZoneRepository repository;

    @Override
    public ZoneDTO saveZone(ZoneDTO zoneDTO) {
        // 1. Validation Logic
        if (zoneDTO.getMinTemp() >= zoneDTO.getMaxTemp()) {
            throw new RuntimeException("Validation Error: minTemp must be less than maxTemp");
        }

        // 2. Map DTO to Entity
        Zone zone = new Zone();
        zone.setZoneName(zoneDTO.getZoneName());
        zone.setDescription(zoneDTO.getDescription());
        zone.setMinTemp(zoneDTO.getMinTemp());
        zone.setMaxTemp(zoneDTO.getMaxTemp());

        zone.setDeviceId("IOT-DEV-" + System.currentTimeMillis());

        Zone savedZone = repository.save(zone);

        // 3. Map Saved Entity back to DTO
        zoneDTO.setId(savedZone.getId());
        zoneDTO.setDeviceId(savedZone.getDeviceId());
        return zoneDTO;
    }

    @Override
    public List<ZoneDTO> getAllZones() {
        return repository.findAll().stream().map(zone -> new ZoneDTO(
                zone.getId(),
                zone.getZoneName(),
                zone.getDescription(),
                zone.getMinTemp(),
                zone.getMaxTemp(),
                zone.getDeviceId()
        )).collect(Collectors.toList());
    }

    @Override
    public ZoneDTO getZoneById(Integer id) {
        Zone zone = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Zone not found with id: " + id));

        return new ZoneDTO(zone.getId(), zone.getZoneName(), zone.getDescription(),
                zone.getMinTemp(), zone.getMaxTemp(), zone.getDeviceId());
    }

    @Override
    public void deleteZone(Integer id) {
        repository.deleteById(id);
    }
}
