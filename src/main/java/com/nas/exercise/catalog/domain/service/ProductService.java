package com.nas.exercise.catalog.domain.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.nas.exercise.catalog.domain.model.Product;

public interface ProductService {
    Product create(Product product);
    Page<Product> getAll(Pageable pageable);
    Page<Product> getAllByCategoryId(Integer categoryId, Pageable pageable);
    Product getById(Integer productId);
    Product getByName(String name);
    Product updateById(Integer productId, String newName, String newDescription, Float newPrice, String newImageUrl);
    Boolean deleteById(Integer productId);
}
