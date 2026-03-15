package com.agms.automation_service.service.impl;

import com.agms.automation_service.client.ZoneClient;
import com.agms.automation_service.dto.TelemetryRequest;
import com.agms.automation_service.entity.AutomationLog;
import com.agms.automation_service.repository.AutomationLogRepository;
import com.agms.automation_service.service.AutomationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class AutomationServiceImpl implements AutomationService {

    @Autowired
    private ZoneClient zoneClient;

    @Autowired
    private AutomationLogRepository repository;

    @Override
    public void processTelemetry(TelemetryRequest data) {
        
        // fetch all zones from the zone service
        List<Map<String, Object>> zones = zoneClient.getAllZones();

        if (zones == null) return;

        for (Map<String, Object> zone : zones) {
            // check if the telemetry data's deviceId matches the zone's deviceId
            if (zone.get("deviceId") != null && zone.get("deviceId").toString().equals(data.getDeviceId())) {


                double currentTemp = Double.parseDouble(data.getValue().get("temperature").toString());
                double minTemp = Double.parseDouble(zone.get("minTemp").toString());
                double maxTemp = Double.parseDouble(zone.get("maxTemp").toString());

                String action;

                if (currentTemp > maxTemp) {
                    action = "TURN_FAN_ON";
                } else if (currentTemp < minTemp) {
                    action = "TURN_HEATER_ON";
                } else {
                    // temperature is within the optimal range, so we can turn off all devices
                    action = "OPTIMAL_RANGE_OFF_ALL";
                }

                // save the action to the database
                AutomationLog log = new AutomationLog();
                log.setDeviceId(data.getDeviceId());
                log.setAction(action);
                log.setTemperature(currentTemp);
                log.setTimestamp(LocalDateTime.now());

                repository.save(log);

                // Debug log
                System.out.println(">>> [LOGGING] Temp: " + currentTemp + " | Action: " + action + " for " + data.getDeviceId());
            }
        }
    }

    @Override
    public List<AutomationLog> getAllLogs() {
        return repository.findAll();
    }
}