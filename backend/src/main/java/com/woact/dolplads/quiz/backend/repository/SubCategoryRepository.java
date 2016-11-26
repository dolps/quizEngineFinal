package com.woact.dolplads.quiz.backend.repository;

import com.woact.dolplads.quiz.backend.entity.SubCategory;
import com.woact.dolplads.quiz.backend.entity.SubSubCategory;

import java.util.List;

/**
 * Created by dolplads on 18/11/2016.
 */
@SuppressWarnings("unchecked")
public class SubCategoryRepository extends CrudRepository<Long, SubCategory> {
    public SubCategoryRepository() {
        super(SubCategory.class);
    }

    public List<SubCategory> findAllByParentId(Long parentId) {
        return entityManager.createQuery("select sub from SubCategory sub where sub.parentCategory.id = :parentId")
                .setParameter("parentId", parentId)
                .getResultList();
    }

    public List<SubSubCategory> findAllSubSubCategories(Long id) {
        return entityManager.createQuery("select subsub.childCategories from SubCategory subsub where subsub.id = :sId")
                .setParameter("sId", id)
                .getResultList();
    }
}
