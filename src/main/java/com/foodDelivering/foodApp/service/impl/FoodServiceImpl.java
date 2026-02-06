package com.foodDelivering.foodApp.service.impl;

import com.foodDelivering.foodApp.exception.ImageUploadException;
import com.foodDelivering.foodApp.model.FileCategory;
import com.foodDelivering.foodApp.model.FoodProductModel.FoodCategory;
import com.foodDelivering.foodApp.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import java.io.IOException;
import java.time.Duration;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FoodServiceImpl implements FoodService {

    private final S3Client s3Client;
    private final S3Presigner s3Presigner;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    @Override
    public String uploadFile(MultipartFile file, FileCategory category, FoodCategory foodCategory, Long id) {

        try{
            String filename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

            String s3key = category.getPrefix();

           if( category == FileCategory.PRODUCTS && foodCategory != null && id != null){
               s3key += foodCategory.getFolderName() + "/" + id.toString() + "/" + filename;
           }else if(id != null){
               s3key += id.toString() + "/" + filename;
           }
           else {
            s3key += filename;
           }

            s3Client.putObject(
                    PutObjectRequest.builder()
                            .bucket(bucketName)
                            .key(s3key)
                            .contentType(file.getContentType())
                            .build(),
                    RequestBody.fromInputStream(file.getInputStream(), file.getSize())
            );

            return s3key;

        }catch (IOException e){
            throw new ImageUploadException("Failed to upload file: "+e.getMessage());
        }
    }

    @Override
    public String generateSignedUrl(String s3key) {

        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(s3key)
                .build();

        GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                .signatureDuration(Duration.ofHours(1))
                .getObjectRequest(getObjectRequest)
                .build();

        PresignedGetObjectRequest presignedGetObjectRequest = s3Presigner.presignGetObject(presignRequest);
        return presignedGetObjectRequest.url().toString();
    }
}
