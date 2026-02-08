package com.foodDelivering.foodApp.repository.OrderTempRepository;

import com.foodDelivering.foodApp.model.OrdersModel.OrderTemp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderTempRepository extends JpaRepository<OrderTemp,Long> {
}
