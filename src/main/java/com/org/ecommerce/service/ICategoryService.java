package com.org.ecommerce.service;

import com.org.ecommerce.model.Category;

import java.util.List;

public interface ICategoryService {
    List<Category> getAll();
    void create(Category category);
    void delete(Long id);
    void update(Long id, Category category);
}
