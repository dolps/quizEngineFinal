package com.woact.dolplads.quiz.rest.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

/**
 * Created by dolplads on 07/11/2016.
 */
@ApiModel("Category")
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
