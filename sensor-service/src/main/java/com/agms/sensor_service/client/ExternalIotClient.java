package com.agms.sensor_service.client;

import com.agms.sensor_service.dto.ExternalAuthRequest;
import com.agms.sensor_service.dto.TelemetryResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@FeignClient(name = "external-iot-telemetry", url = "${external.iot.base-url}")

public interface ExternalIotClient {

    @PostMapping("/auth/login")
    Map<String, Object> login(@RequestBody ExternalAuthRequest request);

    @GetMapping("/devices/telemetry/{deviceId}")
    TelemetryResponse getTelemetry(@RequestHeader("Authorization") String token, @PathVariable String deviceId);

}
