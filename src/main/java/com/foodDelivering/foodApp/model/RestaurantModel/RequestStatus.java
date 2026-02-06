package com.foodDelivering.foodApp.model.RestaurantModel;

public enum RequestStatus {
    PENDING,     // Waiting for admin review
    APPROVED,    // Approved → create Restaurant + update User.role=OWNER
    REJECTED
}
