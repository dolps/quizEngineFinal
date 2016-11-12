package com.woact.dolplads.quiz.rest.dto;

import com.woact.dolplads.quiz.rest.backend.entity.Category;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by dolplads on 07/11/2016.
 */
public class CategoryConverter {
    private CategoryConverter() {
    }

    public static CategoryDto transform(Category category) {
        Objects.requireNonNull(category);
        return new CategoryDto(category.getId(), category.getCategoryText());
    }

    public static List<CategoryDto> transform(List<Category> categories) {
        Objects.requireNonNull(categories);

        return categories.stream().map(CategoryConverter::transform).collect(Collectors.toList());
    }
}
