package com.woact.dolplads.quiz.rest.dto.category;

import com.woact.dolplads.quiz.backend.entity.Category;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;

/**
 * Created by dolplads on 07/11/2016.
 */
@ApiModel("Category")
@ToString
public class CategoryDto {
    @ApiModelProperty("The id of the category")
    public Long id;

    @ApiModelProperty("The text of the category")
    public String categoryText;

    public CategoryDto(Long id, String categoryText) {
        this.id = id;
        this.categoryText = categoryText;
    }

    public CategoryDto() {
    }
}
