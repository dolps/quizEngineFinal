package com.woact.dolplads.quiz.backend.service;

import com.woact.dolplads.quiz.backend.contract.CRUD;
import com.woact.dolplads.quiz.backend.entity.SubCategory;
import com.woact.dolplads.quiz.backend.entity.SubSubCategory;
import com.woact.dolplads.quiz.backend.repository.SubCategoryRepository;
import lombok.NoArgsConstructor;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

/**
 * Created by dolplads on 18/11/2016.
 * <p>
 * needs no argsconstructor
 */
@Stateless
@NoArgsConstructor
public class SubCategoryService extends CommonCRUDService<Long, SubCategory> {
    private SubCategoryRepository subCategoryRepository;

    @Inject
    public SubCategoryService(SubCategoryRepository subCategoryRepository) {
        super(subCategoryRepository);
        this.subCategoryRepository = subCategoryRepository;
    }

    public List<SubCategory> findAllByParentId(Long parentId) {
        return subCategoryRepository.findAllByParentId(parentId);
    }

    public List<SubSubCategory> findAllSubSubCategoriesBySubCategoryId(Long id) {
        return subCategoryRepository.findAllSubSubCategories(id);
    }
}
