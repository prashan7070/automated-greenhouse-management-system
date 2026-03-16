package com.agms.crop_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;

@FeignClient(name = "zone-service")
public interface ZoneClient {

    @GetMapping("/api/zones/{id}")
    Map<String, Object> getZoneById(@RequestHeader("Authorization") String token, @PathVariable Integer id);
}