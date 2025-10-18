package com.Take_Home_Exercise.Take_Home_Exercise.repository;

import com.Take_Home_Exercise.Take_Home_Exercise.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, String>, JpaSpecificationExecutor<Product> {

}
