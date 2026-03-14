package com.agms.sensor_service.scheduler;

import com.agms.sensor_service.service.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TelemetryScheduler {

    @Autowired
    private SensorService sensorService;

    @Scheduled(fixedRate = 10000) // Run every 10 seconds
    public void runTelemetryTask() {
        sensorService.fetchAndProcessTelemetry();
    }
}
