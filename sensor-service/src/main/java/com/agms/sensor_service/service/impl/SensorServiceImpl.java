package com.agms.sensor_service.service.impl;

import com.agms.sensor_service.client.*;
import com.agms.sensor_service.dto.*;
import com.agms.sensor_service.service.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class SensorServiceImpl implements SensorService {

    @Autowired private ZoneClient zoneClient;
    @Autowired private ExternalIotClient iotClient;
    @Autowired private AutomationClient automationClient;

    @Value("${external.iot.username}") private String username;
    @Value("${external.iot.password}") private String password;

    private TelemetryResponse lastFetchedData; // For testing/demo purposes

    @Override
    public void fetchAndProcessTelemetry() {
        try {
            // External Login
            ExternalAuthRequest authReq = new ExternalAuthRequest(username, password);
            String token = "Bearer " + iotClient.login(authReq).get("accessToken").toString();

            // Get All Registered Zones
            List<Map<String, Object>> zones = zoneClient.getAllZones();

            for (Map<String, Object> zone : zones) {
                String deviceId = zone.get("deviceId").toString();

                // Fetch Live Telemetry
                TelemetryResponse data = iotClient.getTelemetry(token, deviceId);
                this.lastFetchedData = data;

                //Push to Automation Service
                automationClient.sendTelemetryToProcess(data);

                System.out.println(">>> Telemetry Sent for Device: " + deviceId);
            }
        } catch (Exception e) {
            System.err.println("Sensor Service Error: " + e.getMessage());
        }
    }

    public TelemetryResponse getLastData() { return lastFetchedData; }
}
