package com.agms.sensor_service.dto;

import lombok.Data;
import java.util.Map;

@Data
public class TelemetryResponse {
    private String deviceId;
    private String zoneId;
    private Map<String, Object> value; // Temperature, Humidity, etc.
    private String capturedAt;
}