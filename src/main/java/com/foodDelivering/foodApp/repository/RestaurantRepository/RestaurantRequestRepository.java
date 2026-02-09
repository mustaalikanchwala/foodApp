package com.foodDelivering.foodApp.repository.RestaurantRepository;

import com.foodDelivering.foodApp.model.RestaurantModel.RestaurantRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRequestRepository extends JpaRepository<RestaurantRequest,Long> {
}
