package com.agms.crop_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CropBatch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String cropName;
    private String zoneId; // zone where the crop is planted
    private LocalDate plantedDate;
    private String status; // SEEDLING, VEGETATIVE, HARVESTED
}