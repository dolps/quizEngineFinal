package com.woact.dolplads.quiz.rest.dto.category;

import com.woact.dolplads.quiz.backend.entity.SubCategory;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.NoArgsConstructor;

/**
 * Created by dolplads on 21/11/2016.
 */
@ApiModel("A SubSubCategory")
@NoArgsConstructor
public class SubSubCategoryDto {
    @ApiModelProperty(value = "The id of the subsubcategory", readOnly = true, dataType = "Long", notes = "unique id will be created during persist")
    public Long id;
    @ApiModelProperty(value = "The parentCategory of the subsubcategory")
    public SubCategory parentCategory;
    @ApiModelProperty(value = "The text of the subsubcategory")
    public String categoryText;

    public SubSubCategoryDto(Long id, SubCategory parentCategory, String categoryText) {
        this.id = id;
        this.parentCategory = parentCategory;
        this.categoryText = categoryText;
    }
}
