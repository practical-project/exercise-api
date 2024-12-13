package com.nas.exercise.catalog.service;

import org.springframework.stereotype.Service;

import com.nas.exercise.catalog.domain.model.Category;
import com.nas.exercise.catalog.domain.persistence.CategoryRepository;
import com.nas.exercise.catalog.domain.service.CategoryService;
import com.nas.exercise.core.ValidatorUtil;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{
    private final CategoryRepository repository;
    private final ValidatorUtil validatorUtil;

    @Override
    public Category create(Category category) {
        validatorUtil.validate(category);
        repository.findByName(category.getName()).ifPresent(existing -> {
            throw new DataIntegrityViolationException("El nombre de la categoría ya existe");
        });

        return repository.save(category);
    }

    @Override
    public Page<Category> getAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Category getById(Integer categoryId) {
        return repository.findById(categoryId)
            .orElseThrow(() -> new EntityNotFoundException("Categoria no encontrada"));
    }

    @Override
    public Category getByName(String name) {
        return repository.findByName(name)
            .orElseThrow(() -> new EntityNotFoundException("Categoria no encontrada"));
    }

    @Override
    @Transactional
    public Category updateById(Integer categoryId, String newName, String newDescription){
        Category category = repository.findById(categoryId)
            .orElseThrow(() -> new EntityNotFoundException("Categoria no encontrada"));

        repository.findByName(newName).ifPresent(existingCategory -> {
            if (!existingCategory.getId().equals(categoryId)) { // Validar que NO sea el mismo producto
                throw new DataIntegrityViolationException("El nombre de la categoría ya existe");
            }
        });

        category.setName(newName);
        category.setDescription(newDescription);

        return repository.save(category);
    }

    @Override
    @Transactional
    public Boolean deleteById(Integer categoryId) {
        Category category = repository.findById(categoryId)
            .orElseThrow(() -> new EntityNotFoundException("Categoria no encontrada"));

        repository.delete(category);
        return true;
    }

}
