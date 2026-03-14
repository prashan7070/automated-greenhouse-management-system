package com.agms.sensor_service.controller;

import com.agms.sensor_service.dto.TelemetryResponse;
import com.agms.sensor_service.service.impl.SensorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sensors")
public class SensorController {

    @Autowired
    private SensorServiceImpl sensorService;

    @GetMapping("/latest")
    public TelemetryResponse getLatest() {
        return sensorService.getLastData();
    }
}
