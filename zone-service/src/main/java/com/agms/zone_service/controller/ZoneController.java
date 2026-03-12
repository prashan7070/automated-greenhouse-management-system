package com.agms.zone_service.controller;

import com.agms.zone_service.dto.ZoneDTO;
import com.agms.zone_service.service.ZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/zones")
public class ZoneController {

    @Autowired
    private ZoneService service; 

    @PostMapping
    public ZoneDTO createZone(@RequestBody ZoneDTO zoneDTO) {
        return service.saveZone(zoneDTO);
    }

    @GetMapping
    public List<ZoneDTO> getAllZones() {
        return service.getAllZones();
    }

    @GetMapping("/{id}")
    public ZoneDTO getZoneById(@PathVariable Integer id) {
        return service.getZoneById(id);
    }

    @DeleteMapping("/{id}")
    public String deleteZone(@PathVariable Integer id) {
        service.deleteZone(id);
        return "Zone deleted successfully!";
    }
}
