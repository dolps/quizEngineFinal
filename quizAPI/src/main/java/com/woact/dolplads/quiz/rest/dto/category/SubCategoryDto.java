package com.woact.dolplads.quiz.rest.dto.category;

import com.woact.dolplads.quiz.backend.entity.Category;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.NoArgsConstructor;

/**
 * Created by dolplads on 18/11/2016.
 */
@ApiModel("A Subcategory")
@NoArgsConstructor
public class SubCategoryDto {
    @ApiModelProperty(value = "The id of the subcategory", readOnly = true, dataType = "Long", notes = "unique id will be created during persist")
    public Long id;
    @ApiModelProperty("subcategory description")
    public String categoryText;
    @ApiModelProperty("The parentCategory referenced category")
    public Category parentCategory;

    public SubCategoryDto(Long id, Long parentCategoryId, String categoryText) {
        this.id = id;
        this.categoryText = categoryText;
        this.parentCategory = new Category();
        parentCategory.setId(parentCategoryId);
    }
}
