package com.javaee2.dolplads.api;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by dolplads on 28/11/2016.
 */
@ApiModel("a games")
public class GameDto {
    @ApiModelProperty(value = "id of the game", hidden = true)
    public Long id;
    @ApiModelProperty("the number of quizzes")
    public int numberOfQuizzes;
    @ApiModelProperty("url to the quiz api")
    public String uriToQuizApi; // make sure its not reaveiling the answer of the quizzes
}
