package com.nas.exercise.catalog.domain.service;

import com.nas.exercise.catalog.domain.model.Category;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    Category create(Category category);
    Page<Category> getAll(Pageable pageable);
    Category getById(Integer categoryId);
    Category getByName(String name);
    Category updateById(Integer categoryId, String newName, String newDescription);
    Boolean deleteById(Integer categoryId);
} 
