package com.foodDelivering.foodApp.repository.AddressRepsitory;

import com.foodDelivering.foodApp.model.Address.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepsitory extends JpaRepository<Address,Long> {
}
