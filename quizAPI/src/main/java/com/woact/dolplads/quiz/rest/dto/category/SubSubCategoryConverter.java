package com.woact.dolplads.quiz.rest.dto.category;

import com.woact.dolplads.quiz.backend.entity.SubCategory;
import com.woact.dolplads.quiz.backend.entity.SubSubCategory;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by dolplads on 21/11/2016.
 */
public class SubSubCategoryConverter {

    // todo implement subcategory
    public static SubSubCategoryDto transform(SubSubCategory subCategory) {
        Objects.requireNonNull(subCategory);

        return new SubSubCategoryDto(subCategory.getId(),subCategory.getParentCategory(),subCategory.getCategoryText());
    }

    public static List<SubSubCategoryDto> transform(List<SubSubCategory> subCategories) {
        Objects.requireNonNull(subCategories);
        return subCategories.stream().map(SubSubCategoryConverter::transform).collect(Collectors.toList());
    }
}
