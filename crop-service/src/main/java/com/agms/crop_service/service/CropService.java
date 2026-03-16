package com.agms.crop_service.service;

import com.agms.crop_service.dto.CropDTO;
import java.util.List;

public interface CropService {

    CropDTO registerBatch(CropDTO cropDTO,String token);
    List<CropDTO> getAllBatches();
    CropDTO updateStatus(Integer id, String status);

}