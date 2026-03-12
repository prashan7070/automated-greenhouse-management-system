package com.agms.zone_service.service;

import com.agms.zone_service.dto.ZoneDTO;
import java.util.List;

public interface ZoneService {

    ZoneDTO saveZone(ZoneDTO zoneDTO);
    List<ZoneDTO> getAllZones();
    ZoneDTO getZoneById(Integer id);
    void deleteZone(Integer id);

}