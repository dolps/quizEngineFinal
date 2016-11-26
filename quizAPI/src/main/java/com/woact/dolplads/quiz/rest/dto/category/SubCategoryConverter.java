package com.woact.dolplads.quiz.rest.dto.category;

import com.woact.dolplads.quiz.backend.entity.SubCategory;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by dolplads on 18/11/2016.
 */
public class SubCategoryConverter {

    // todo implement subcategory
    public static SubCategoryDto transform(SubCategory subCategory) {
        Objects.requireNonNull(subCategory);

        return new SubCategoryDto(subCategory.getId(), subCategory.getParentCategory().getId(), subCategory.getCategoryText());
    }

    public static List<SubCategoryDto> transform(List<SubCategory> subCategories) {
        Objects.requireNonNull(subCategories);
        return subCategories.stream().map(SubCategoryConverter::transform).collect(Collectors.toList());
    }
}
