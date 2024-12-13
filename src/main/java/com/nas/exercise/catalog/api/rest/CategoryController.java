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

import com.nas.exercise.catalog.domain.service.CategoryService;
import com.nas.exercise.catalog.mapping.CategoryMapper;
import com.nas.exercise.catalog.resource.request.CreateCategoryRequest;
import com.nas.exercise.catalog.resource.request.UpdateCategoryRequest;
import com.nas.exercise.catalog.resource.response.CategoryResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/api/v1/categories", produces = "application/json")
@RequiredArgsConstructor
public class CategoryController {
    
    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    // GET /api/v1/categories
    @GetMapping
    public Page<CategoryResponse> getAllCategories(Pageable pageable) {
        return categoryMapper.modelsToReponsesPage(categoryService.getAll(pageable));
    }

    // POST /api/v1/categories
    @PostMapping
    public CategoryResponse createCategory(@RequestBody CreateCategoryRequest request) {
        return categoryMapper.modelToResponse(categoryService.create(categoryMapper.createRequestToModel(request)));
    }

    // GET /api/v1/categories/{id}
    @GetMapping("/{id}")
    public CategoryResponse getCategoryById(@PathVariable("id") Integer id) {
        return categoryMapper.modelToResponse(categoryService.getById(id));
    }

    // GET /api/v1/categories/name/{name}
    @GetMapping("/name/{name}")
    public CategoryResponse getCategoryByName(@PathVariable("name") String name) {
        return categoryMapper.modelToResponse(categoryService.getByName(name));
    }

    // PUT /api/v1/categories/{id}
    @PutMapping("/{id}")
    public CategoryResponse updateCategoryById(@PathVariable("id") Integer id, @RequestBody UpdateCategoryRequest request) {
        return categoryMapper.modelToResponse(categoryService.updateById(id, request.getName(), request.getDescription()));
    }

    // DELETE /api/v1/categories/{id}
    @DeleteMapping("/{id}")
    public Boolean deleteCategoryById(@PathVariable("id") Integer id) {
        return categoryService.deleteById(id);
    }
}
