package com.nas.exercise.catalog.service;

import org.springframework.stereotype.Service;

import com.nas.exercise.catalog.domain.model.Product;
import com.nas.exercise.catalog.domain.persistence.ProductRepository;
import com.nas.exercise.catalog.domain.service.ProductService;
import com.nas.exercise.core.ValidatorUtil;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{
        private final ProductRepository repository;
        private final ValidatorUtil validatorUtil;

    @Override
    public Product create(Product product) {
        validatorUtil.validate(product);
        repository.findByName(product.getName()).ifPresent(existing -> {
            throw new DataIntegrityViolationException("El nombre del producto ya existe");
        });

        return repository.save(product);
    }

    @Override
    public Page<Product> getAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Page<Product> getAllByCategoryId(Integer categoryId, Pageable pageable) {
        return repository.findAllByCategoryId(categoryId, pageable);
    }

    @Override
    public Product getById(Integer productId) {
        return repository.findById(productId)
            .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado"));
    }

    @Override
    public Product getByName(String name) {
        return repository.findByName(name)
            .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado"));
    }

    @Override
    @Transactional
    public Product updateById(Integer productId, String newName, String newDescription, Float newPrice, String newImageUrl){
        Product product = repository.findById(productId)
            .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado"));

        repository.findByName(newName).ifPresent(existingProduct -> {
            if (!existingProduct.getId().equals(productId)) { // Validar que NO sea el mismo producto
                throw new DataIntegrityViolationException("El nombre del producto ya existe");
            }
        });

        product.setName(newName);
        product.setDescription(newDescription);
        product.setPrice(newPrice);
        product.setImageUrl(newImageUrl);

        return repository.save(product);
    }

    @Override
    @Transactional
    public Boolean deleteById(Integer productId) {
        Product product = repository.findById(productId)
            .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado"));

        repository.delete(product);
        return true;
    }
}
