package com.nas.exercise.catalog.domain.persistence;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nas.exercise.catalog.domain.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Optional<Product> findByName(String name);
    Page<Product> findAllByCategoryId(Integer categoryId, Pageable pageable);
}

