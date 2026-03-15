package com.agms.sensor_service.service.impl;

import com.agms.sensor_service.client.AutomationClient;
import com.agms.sensor_service.client.ExternalIotClient;
import com.agms.sensor_service.client.ZoneClient;
import com.agms.sensor_service.dto.ExternalAuthRequest;
import com.agms.sensor_service.dto.TelemetryResponse;
import com.agms.sensor_service.service.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SensorServiceImpl implements SensorService {

    @Autowired
    private ZoneClient zoneClient;

    @Autowired
    private ExternalIotClient iotClient;

    @Autowired
    private AutomationClient automationClient;

    // data from config server
    @Value("${external.iot.username}")
    private String username;

    @Value("${external.iot.password}")
    private String password;

    private TelemetryResponse lastFetchedData;

    @Override
    public void fetchAndProcessTelemetry() {
        //Debug Logs
        System.out.println(">>> CHECKING CONFIG: Username=" + username + ", Password=" + password);

        if (username == null || password == null) {
            System.err.println("!!! ERROR: Credentials not found in Config Server. Did you commit the YAML file?");
            return;
        }

        try {
            // login to external API to get token
            ExternalAuthRequest authReq = new ExternalAuthRequest(username, password);
            Map<String, Object> authResponse = iotClient.login(authReq);

            if (authResponse == null || !authResponse.containsKey("accessToken")) {
                System.err.println("!!! ERROR: Failed to get Access Token from External API");
                return;
            }

            String token = "Bearer " + authResponse.get("accessToken").toString();

            // get zones from zone service
            List<Map<String, Object>> zones = zoneClient.getAllZones();
            if (zones == null || zones.isEmpty()) {
                System.out.println(">>> INFO: No zones found to fetch telemetry.");
                return;
            }

            for (Map<String, Object> zone : zones) {
                if (zone.get("deviceId") == null) continue;

                String deviceId = zone.get("deviceId").toString();

                // read telemetry data for each device from external API
                TelemetryResponse data = iotClient.getTelemetry(token, deviceId);

                //check if data is null or value is null before processing
                if (data != null && data.getValue() != null) {
                    this.lastFetchedData = data;
                    System.out.println(">>> SUCCESS: Fetched data for " + deviceId + ": " + data.getValue());

                     automationClient.sendTelemetryToProcess(data);
                    
                } else {
                    System.out.println("!!! WARNING: Telemetry data for " + deviceId + " is null");
                }
            }

        } catch (Exception e) {
            System.err.println("!!! SENSOR SERVICE EXCEPTION: " + e.getMessage());
        }
    }

    public TelemetryResponse getLastData() {
        return lastFetchedData;
    }
}