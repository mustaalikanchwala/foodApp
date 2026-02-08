package com.foodDelivering.foodApp.repository.OrderRepository;

import com.foodDelivering.foodApp.model.OrdersModel.OrderItem;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersItemsRepository extends JpaRepository<OrderItem,Long> {
}
