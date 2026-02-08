package com.foodDelivering.foodApp.repository.OrderTempRepository;

import com.foodDelivering.foodApp.model.OrdersModel.OrderItemTemp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemTempRepository extends JpaRepository<OrderItemTemp,Long> {
}
