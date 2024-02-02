package io.platformengineer.service;

import io.platformengineer.dao.CategoryDAO;
import io.platformengineer.model.Category;

import java.util.List;

public class CategoryServiceImpl implements CategoryService {

    private final CategoryDAO categoryDAO;

    public CategoryServiceImpl(CategoryDAO categoryDAO) {
        this.categoryDAO = categoryDAO;
    }

    @Override
    public Category createCategory(Category category) {
        // Here, add any business logic or validations needed before creating a category
        return categoryDAO.createCategory(category);
    }

    @Override
    public Category getCategoryById(Long id) {
        // Directly retrieving from DAO, add any processing if needed
        return categoryDAO.getCategoryById(id);
    }

    @Override
    public Category updateCategory(Category category) {
        // Validate or process data as necessary before updating
        return categoryDAO.updateCategory(category);
    }

    @Override
    public void deleteCategory(Long id) {
        // Any cleanup or additional checks before deletion
        categoryDAO.deleteCategory(id);
    }

    @Override
    public List<Category> getAllCategories() {
        // Retrieve all categories, possibly process or sort them before returning
        return categoryDAO.getAllCategories();
    }
}
