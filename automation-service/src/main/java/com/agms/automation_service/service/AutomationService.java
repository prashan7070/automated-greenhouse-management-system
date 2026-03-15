package com.agms.automation_service.service;

import com.agms.automation_service.dto.TelemetryRequest;
import com.agms.automation_service.entity.AutomationLog;
import java.util.List;

public interface AutomationService {

    void processTelemetry(TelemetryRequest data);
    List<AutomationLog> getAllLogs();

}