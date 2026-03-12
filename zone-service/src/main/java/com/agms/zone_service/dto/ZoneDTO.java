package com.agms.zone_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ZoneDTO {
    private Integer id;
    private String zoneName;
    private String description;
    private Double minTemp;
    private Double maxTemp;
    private String deviceId;
}