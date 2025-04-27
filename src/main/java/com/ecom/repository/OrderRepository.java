package com.ecom.repository;

import com.ecom.entity.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Integer> {

    List<Order> findByCustomerIdOrderByCreatedDesc(Integer customerId, Pageable pageable);

}
