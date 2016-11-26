package com.woact.dolplads.quiz.rest.dto.quiz;

import com.woact.dolplads.quiz.backend.entity.AnswerSet;
import com.woact.dolplads.quiz.backend.entity.Question;
import com.woact.dolplads.quiz.backend.entity.SubCategory;
import com.woact.dolplads.quiz.backend.entity.SubSubCategory;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by dolplads on 12/11/2016.
 */
@ApiModel("A quiz")
public class QuizDto {
    @ApiModelProperty("The id of the quiz")
    public Long id;
    @ApiModelProperty("The category this quiz belongs to")
    public Long subsubCategoryId;
    @ApiModelProperty("The quizzes question")
    public Question question;
    @ApiModelProperty("The set of answers one is correct rest should be false")
    public AnswerSet answerSet;
}
