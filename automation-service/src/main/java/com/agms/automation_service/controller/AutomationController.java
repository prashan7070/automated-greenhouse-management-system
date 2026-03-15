package com.agms.automation_service.controller;

import com.agms.automation_service.dto.TelemetryRequest;
import com.agms.automation_service.entity.AutomationLog;
import com.agms.automation_service.service.AutomationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/automation")
public class AutomationController {

    @Autowired
    private AutomationService automationService;

    // get data from sensor service and process it
    @PostMapping("/process")
    public void receiveTelemetry(@RequestBody TelemetryRequest data) {
        automationService.processTelemetry(data);
    }

    // check logs of automation actions
    @GetMapping("/logs")
    public List<AutomationLog> getLogs() {
        return automationService.getAllLogs();
    }
}