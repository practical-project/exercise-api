package com.nas.exercise.catalog.mapping;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.nas.exercise.catalog.domain.model.Category;
import com.nas.exercise.catalog.resource.request.CreateCategoryRequest;
import com.nas.exercise.catalog.resource.response.CategoryResponse;
import com.nas.exercise.core.ValidatorUtil;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CategoryMapper {
    private final ValidatorUtil validatorUtil;

    public Category createRequestToModel(CreateCategoryRequest request) {
        validatorUtil.validate(request);
        return Category.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();

    }

    public CategoryResponse modelToResponse(Category category) {
        return CategoryResponse.builder()
        .id(category.getId())
        .name(category.getName())
        .description(category.getDescription())
        .build();
    }

    public Page<CategoryResponse> modelsToReponsesPage(Page<Category> categoriesPage) {
        return categoriesPage.map(this::modelToResponse);
    }
}