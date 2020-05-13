package com.onepoint.test.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.onepoint.test.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
