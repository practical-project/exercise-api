package com.nas.exercise.catalog.api.rest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nas.exercise.catalog.domain.service.ProductService;
import com.nas.exercise.catalog.mapping.ProductMapper;
import com.nas.exercise.catalog.resource.request.CreateProductRequest;
import com.nas.exercise.catalog.resource.request.UpdateProductRequest;
import com.nas.exercise.catalog.resource.response.ProductResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/api/v1/products", produces = "application/json")
@RequiredArgsConstructor
public class ProductController {
    
    private final ProductService productService;
    private final ProductMapper productMapper;

    // GET /api/v1/products
    @GetMapping
    public Page<ProductResponse> getAllProducts(Pageable pageable) {
        return productMapper.modelsToReponsesPage(productService.getAll(pageable));
    }

    // GET /api/v1/products/category/{id}
    @GetMapping("/category/{id}")
    public Page<ProductResponse> getAllProductsByCategoryId(@PathVariable("id") Integer categoryId, Pageable pageable) {
        return productMapper.modelsToReponsesPage(productService.getAllByCategoryId(categoryId, pageable));
    }

    // POST /api/v1/products
    @PostMapping
    public ProductResponse createProduct(@RequestBody CreateProductRequest request) {
        return productMapper.modelToResponse(productService.create(productMapper.createRequestToModel(request)));
    }

    // GET /api/v1/products/{id}
    @GetMapping("/{id}")
    public ProductResponse getProductById(@PathVariable("id") Integer id) {
        return productMapper.modelToResponse(productService.getById(id));
    }

    // GET /api/v1/products/name/{name}
    @GetMapping("/name/{name}")
    public ProductResponse getProductByName(@PathVariable("name") String name) {
        return productMapper.modelToResponse(productService.getByName(name));
    }

    // PUT /api/v1/products/{id}
    @PutMapping("/{id}")
    public ProductResponse updateProductById(@PathVariable("id") Integer id, @RequestBody UpdateProductRequest request) {
        return productMapper.modelToResponse(productService.updateById(id, request.getName(), request.getDescription(), request.getPrice(), request.getImageUrl()));
    }

    // DELETE /api/v1/products/{id}
    @DeleteMapping("/{id}")
    public Boolean deleteProductById(@PathVariable("id") Integer id) {
        return productService.deleteById(id);
    }
}