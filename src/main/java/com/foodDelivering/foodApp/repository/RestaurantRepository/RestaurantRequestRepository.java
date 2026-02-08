package com.foodDelivering.foodApp.repository.RestaurantRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRequestRepository extends JpaRepository<RestaurantRequestRepository,Long> {
}
