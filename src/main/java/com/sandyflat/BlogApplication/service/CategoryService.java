package com.sandyflat.BlogApplication.service;

import com.sandyflat.BlogApplication.payload.CategoryDTO;

import java.util.List;

public interface CategoryService {
    CategoryDTO createCategory(CategoryDTO categoryDTO);

    CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId);

    void deleteCategory(Long categoryId);

    CategoryDTO getCategoryById(Long categoryId);

    List<CategoryDTO> getAllCategories();
}
