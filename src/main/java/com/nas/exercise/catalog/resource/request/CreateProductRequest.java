package com.nas.exercise.catalog.resource.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductRequest {
    @NotNull
    @NotBlank
    String name;

    @NotNull
    @NotBlank
    String description;

    @NotNull
    Float price;
    
    @NotNull
    @NotBlank
    @JsonProperty("image_url")
    String imageUrl;

    @NotNull
    @JsonProperty("category_id")
    Integer categoryId;
}
