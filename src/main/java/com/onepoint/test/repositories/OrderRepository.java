package com.onepoint.test.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.onepoint.test.entities.Order;


public interface OrderRepository extends JpaRepository<Order, Long>{

}
