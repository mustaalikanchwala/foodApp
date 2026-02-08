package com.foodDelivering.foodApp.repository.OrderRepository;

import com.foodDelivering.foodApp.model.OrdersModel.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Orders,Long> {
}
