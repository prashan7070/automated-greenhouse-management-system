package com.agms.zone_service.service.impl;

import com.agms.zone_service.client.ExternalIotClient;
import com.agms.zone_service.dto.*;
import com.agms.zone_service.entity.Zone;
import com.agms.zone_service.repository.ZoneRepository;
import com.agms.zone_service.service.ZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ZoneServiceImpl implements ZoneService {

    @Autowired private ZoneRepository repository;
    @Autowired private ExternalIotClient iotClient;

    @Value("${external.iot.username}") private String extUsername;
    @Value("${external.iot.password}") private String extPassword;

    @Override
    public ZoneDTO saveZone(ZoneDTO zoneDTO) {
        
        // 1. Validation
        if (zoneDTO.getMinTemp() >= zoneDTO.getMaxTemp()) {
            throw new RuntimeException("Validation Error: minTemp must be less than maxTemp");
        }

        try {

            ExternalAuthRequest authReq = new ExternalAuthRequest(extUsername, extPassword);
            Map<String, Object> authResponse = iotClient.login(authReq);
            String token = "Bearer " + authResponse.get("accessToken").toString();


            ExternalDeviceRequest deviceReq = new ExternalDeviceRequest(zoneDTO.getZoneName(), "ZONE-" + zoneDTO.getZoneName());
            Map<String, Object> deviceResponse = iotClient.registerDevice(token, deviceReq);


            String realDeviceId = deviceResponse.get("deviceId").toString();


            Zone zone = new Zone();
            zone.setZoneName(zoneDTO.getZoneName());
            zone.setDescription(zoneDTO.getDescription());
            zone.setMinTemp(zoneDTO.getMinTemp());
            zone.setMaxTemp(zoneDTO.getMaxTemp());
            zone.setDeviceId(realDeviceId);

            Zone savedZone = repository.save(zone);
            zoneDTO.setId(savedZone.getId());
            zoneDTO.setDeviceId(savedZone.getDeviceId());

            return zoneDTO;

        } catch (Exception e) {
            throw new RuntimeException("External IoT API Error: " + e.getMessage());
        }
    }

    @Override
    public List<ZoneDTO> getAllZones() {
        return repository.findAll().stream().map(z -> new ZoneDTO(
                z.getId(), z.getZoneName(), z.getDescription(), z.getMinTemp(), z.getMaxTemp(), z.getDeviceId()
        )).collect(Collectors.toList());
    }

    @Override
    public ZoneDTO getZoneById(Integer id) {
        Zone z = repository.findById(id).orElseThrow(() -> new RuntimeException("Not Found"));
        return new ZoneDTO(z.getId(), z.getZoneName(), z.getDescription(), z.getMinTemp(), z.getMaxTemp(), z.getDeviceId());
    }

    @Override
    public void deleteZone(Integer id) { repository.deleteById(id); }
}