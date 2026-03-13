package com.agms.zone_service.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ExternalDeviceRequest {
    private String name;
    private String zoneId;
}
