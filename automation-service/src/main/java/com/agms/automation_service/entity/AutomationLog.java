package com.agms.automation_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AutomationLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String deviceId;
    private String action; // TURN_FAN_ON, TURN_HEATER_ON
    private Double temperature;
    private LocalDateTime timestamp;
}
