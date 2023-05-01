package com.plantshop.repository;

import com.plantshop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    //public Product delete(Integer id);
}
