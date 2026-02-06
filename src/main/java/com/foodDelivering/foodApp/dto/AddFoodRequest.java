package com.foodDelivering.foodApp.dto;

import com.foodDelivering.foodApp.model.FileCategory;
import lombok.Builder;

@Builder
public record AddFoodRequest(
        String name,
        String shortDescription,
        String description,
        double price,
        FileCategory category,
        String filename
) {
}
