package com.foodDelivering.foodApp.repository.RestaurantRepository;

import com.foodDelivering.foodApp.model.RestaurantModel.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant,Long> {
}
