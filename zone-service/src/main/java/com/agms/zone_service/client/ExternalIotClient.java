package com.agms.zone_service.client;

import com.agms.zone_service.dto.ExternalAuthRequest;
import com.agms.zone_service.dto.ExternalDeviceRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;

@FeignClient(name = "external-iot-api", url = "${external.iot.base-url}")

public interface ExternalIotClient {

    // Log into External API and get the token
    @PostMapping("/auth/login")
    Map<String, Object> login(@RequestBody ExternalAuthRequest request);

    // register the device and get the device ID from the External API
    @PostMapping("/devices")
    Map<String, Object> registerDevice(
            @RequestHeader("Authorization") String token,
            @RequestBody ExternalDeviceRequest request
    );
    
}