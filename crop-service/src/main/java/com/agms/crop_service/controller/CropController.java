package com.agms.crop_service.controller;

import com.agms.crop_service.dto.CropDTO;
import com.agms.crop_service.service.CropService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/crops")
public class CropController {

    @Autowired
    private CropService cropService;

    @PostMapping
    public CropDTO registerBatch(@RequestBody CropDTO cropDTO) {
        return cropService.registerBatch(cropDTO);
    }

    @GetMapping
    public List<CropDTO> getAllBatches() {
        return cropService.getAllBatches();
    }

    @PutMapping("/{id}/status")
    public CropDTO updateStatus(@PathVariable Integer id, @RequestParam String status) {
        return cropService.updateStatus(id, status);
    }
}