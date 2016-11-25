package com.woact.dolplads.quiz.rest.dto;

import com.woact.dolplads.quiz.backend.entity.AnswerSet;
import com.woact.dolplads.quiz.backend.entity.Question;
import com.woact.dolplads.quiz.backend.entity.SubCategory;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by dolplads on 12/11/2016.
 */
@ApiModel("A category")
public class QuizDto {
    @ApiModelProperty("The id of the category")
    public Long id;
    @ApiModelProperty("The category this category belongs to")
    public SubCategory subsubCategory;
    @ApiModelProperty("The quizzes question")
    public Question question;
    @ApiModelProperty("The set of answers one is correct rest should be false")
    public AnswerSet answerSet;
}
