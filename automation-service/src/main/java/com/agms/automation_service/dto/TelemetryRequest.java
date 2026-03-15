package com.agms.automation_service.dto;

import lombok.Data;
import java.util.Map;

@Data
public class TelemetryRequest {
    private String deviceId;
    private Map<String, Object> value; // data from sensor, e.g., temperature, humidity`
}