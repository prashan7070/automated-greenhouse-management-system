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

    @Autowired private ZoneClient zoneClient;
    @Autowired private AutomationLogRepository repository;

    @Override
    public void processTelemetry(TelemetryRequest data) {

        List<Map<String, Object>> zones = zoneClient.getAllZones();

        for (Map<String, Object> zone : zones) {
            if (zone.get("deviceId") != null && zone.get("deviceId").toString().equals(data.getDeviceId())) {

                double currentTemp = Double.parseDouble(data.getValue().get("temperature").toString());
                double minTemp = Double.parseDouble(zone.get("minTemp").toString());
                double maxTemp = Double.parseDouble(zone.get("maxTemp").toString());

                String action = null;

                //Rule Engine Logic
                if (currentTemp > maxTemp) {
                    action = "TURN_FAN_ON";
                } else if (currentTemp < minTemp) {
                    action = "TURN_HEATER_ON";
                }

                // Log the action if any rule is triggered
                if (action != null) {
                    AutomationLog log = new AutomationLog();
                    log.setDeviceId(data.getDeviceId());
                    log.setAction(action);
                    log.setTemperature(currentTemp);
                    log.setTimestamp(LocalDateTime.now());
                    repository.save(log);
                    System.out.println(">>> [RULE TRIGGERED] " + action + " for Device: " + data.getDeviceId());
                }
            }
        }
    }

    @Override
    public List<AutomationLog> getAllLogs() {
        return repository.findAll();
    }
}