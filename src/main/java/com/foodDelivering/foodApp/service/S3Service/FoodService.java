package com.foodDelivering.foodApp.service.S3Service;

import com.foodDelivering.foodApp.model.FileCategory;
import com.foodDelivering.foodApp.model.FoodProductModel.FoodCategory;
import org.springframework.web.multipart.MultipartFile;

public interface FoodService {

    String uploadFile(MultipartFile file, FileCategory category, FoodCategory foodCategory,Long id);

    String generateSignedUrl(String s3key);
}
