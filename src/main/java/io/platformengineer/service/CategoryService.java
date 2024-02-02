package io.platformengineer.service;

import io.platformengineer.model.Category;

import java.util.List;

public interface CategoryService {
    Category createCategory(Category category);
    Category getCategoryById(Long id);
    Category updateCategory(Category category);
    void deleteCategory(Long id);
    List<Category> getAllCategories();
}
