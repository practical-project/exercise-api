package com.nas.exercise.catalog.resource.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
    Integer id;

    String name;

    String description;

    Float price;

    @JsonProperty("image_url")
    String imageUrl;
}
