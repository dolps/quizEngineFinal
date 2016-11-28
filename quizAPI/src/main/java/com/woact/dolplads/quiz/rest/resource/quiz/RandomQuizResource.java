package com.woact.dolplads.quiz.rest.resource.quiz;

import com.woact.dolplads.quiz.backend.entity.Category;
import com.woact.dolplads.quiz.backend.entity.Quiz;
import com.woact.dolplads.quiz.backend.entity.SubSubCategory;
import com.woact.dolplads.quiz.backend.service.CategoryService;
import com.woact.dolplads.quiz.backend.service.QuizService;

import javax.ejb.EJB;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by dolplads on 26/11/2016.
 */
public class RandomQuizResource implements RandomQuizApi {
    @EJB
    private CategoryService categoryService;

    @EJB
    private QuizService quizService;

    @Override
    public Response getRandomQuiz(String x) {
        List<Long> ids = idsForcategory(x);

        Random rand = new Random(System.currentTimeMillis());
        int randX = rand.nextInt(ids.size());


        return Response.status(307)
                .location(UriBuilder.fromUri("quizzes/" + ids.get(randX)).build()).build();
    }

    @Override
    public List<Long> getRandomQuizzes(@DefaultValue("5") int number, String category) {
        List<Long> ids = idsForcategory(category);
        List<Long> randomIds = new ArrayList<>();
        if (ids.size() < number) {
            throw new WebApplicationException(
                    "there has not been created "
                            + number
                            + " quizzes with the category: "
                            + category + " yet", Response.Status.PRECONDITION_FAILED);
        }

        Collections.shuffle(ids);
        return new ArrayList<>(ids.subList(0, number));
    }


    private List<Long> idsForcategory(String x) {
        List<Category> categoryies = categoryService.findAllWithAtLeastOneSubCategoryWithAtLeastOneSubSubCategoryWithAtLeastOneQuiz();

        List<Quiz> quizzes = quizService.findAll();

        List<Long> ids = new ArrayList<>();

        quizzes.forEach(quiz -> {
            SubSubCategory c = quiz.getSubSubCategory();
            if (x.equals(c.getCategoryText())) {
                ids.add(quiz.getId());
            } else if (x.equals(c.getParentCategory().getCategoryText())) {
                ids.add(quiz.getId());
            } else if (x.equals(c.getParentCategory().getParentCategory().getCategoryText())) {
                ids.add(quiz.getId());
            }
        });
        return ids;
    }
}
