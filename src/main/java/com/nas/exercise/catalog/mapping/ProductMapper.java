package com.nas.exercise.catalog.mapping;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.nas.exercise.catalog.domain.model.Category;
import com.nas.exercise.catalog.domain.model.Product;
import com.nas.exercise.catalog.domain.service.CategoryService;
import com.nas.exercise.catalog.resource.request.CreateProductRequest;
import com.nas.exercise.catalog.resource.response.ProductResponse;
import com.nas.exercise.core.ValidatorUtil;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProductMapper {
    private final ValidatorUtil validatorUtil;
    private final CategoryService categoryService;

    public Product createRequestToModel(CreateProductRequest request) {
        validatorUtil.validate(request);
        Category category = Optional.ofNullable(request.getCategoryId())
                                           .map(categoryService::getById)
                                           .orElse(null);
        
        return Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .imageUrl(request.getImageUrl())
                .category(category)
                .build();
    }

    public ProductResponse modelToResponse(Product product) {
        return ProductResponse.builder()
        .id(product.getId())
        .name(product.getName())
        .description(product.getDescription())
        .price(product.getPrice())
        .imageUrl(product.getImageUrl())
        .build();
    }

    public Page<ProductResponse> modelsToReponsesPage(Page<Product> productsPage) {
        return productsPage.map(this::modelToResponse);
    }
}