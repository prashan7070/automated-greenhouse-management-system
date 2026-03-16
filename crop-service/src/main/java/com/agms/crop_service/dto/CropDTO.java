package com.agms.crop_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CropDTO {
    private Integer id;
    private String cropName;
    private String zoneId;
    private LocalDate plantedDate;
    private String status;
}