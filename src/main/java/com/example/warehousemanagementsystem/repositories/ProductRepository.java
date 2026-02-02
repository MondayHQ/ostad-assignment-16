package com.example.warehousemanagementsystem.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

// Local Imports
import com.example.warehousemanagementsystem.entities.ProductEntity;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {}
