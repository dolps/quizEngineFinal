package com.woact.dolplads.quiz.rest.backend.service;

import com.woact.dolplads.quiz.rest.backend.entity.Category;
import com.woact.dolplads.quiz.rest.backend.entity.CategoryLevel;
import com.woact.dolplads.quiz.rest.backend.entity.Quiz;
import com.woact.dolplads.quiz.rest.backend.entity.SubCategory;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by dolplads on 03/11/2016.
 */
@SuppressWarnings("unchecked")
@Stateless
public class QuizService {
    @PersistenceContext
    private EntityManager entityManager;

    public Category createCategory(Category category) {
        entityManager.persist(category);

        return category;
    }

    public Quiz createQuiz(Quiz quiz) {
        entityManager.persist(quiz);

        return quiz;
    }

    public void removeQuiz(Quiz quiz) {
        entityManager.remove(entityManager.merge(quiz));
    }

    public List<Quiz> findByCategory(Category category) {
        return entityManager.createQuery("select quiz from Quiz quiz where quiz.subsubCategory = :category")
                .setParameter("category", category)
                .getResultList();
    }

    public List<Quiz> findByCategoryString(String category) {
        String clause1 = "quiz.subsubCategory.categoryText = :category";
        String clause2 = "quiz.subsubCategory.parentCategory.categoryText = :category";
        String clause3 = "quiz.subsubCategory.parentCategory.parentCategory.categoryText = :category";

        return entityManager.createQuery("select quiz from Quiz quiz where "
                + clause1
                + " or "
                + clause2
                + " or "
                + clause3)
                .setParameter("category", category)
                .getResultList();
    }

    public List<Quiz> findAll() {
        return entityManager.createQuery("select q from Quiz  q")
                .getResultList();
    }

    public List<Category> findAllCategories() {
        return entityManager.createQuery("select c from Category c").getResultList();
    }

    public Category findCategoryById(Long id) {
        return entityManager.find(Category.class, id);
    }

    public boolean removeCategory(Long id) {
        Category c = entityManager.find(Category.class, id);
        if (c != null) {
            entityManager.remove(c);
            return true;
        } else {
            return false;
        }

    }

    public SubCategory findSubCategoryById(Long id) {
        return entityManager.find(SubCategory.class, id);
    }

    public List<SubCategory> findAllSubCategories(CategoryLevel categoryLevel) {

        return entityManager.createQuery("select subCat from SubCategory subCat where subCat.categoryLevel = :catLevel")
                .setParameter("catLevel", categoryLevel.getValue())
                .getResultList();
    }
}
