package com.org.ecommerce.service;

import com.org.ecommerce.dtos.CategoryDTO;
import com.org.ecommerce.model.Category;
import com.org.ecommerce.payload.response.CategoryResponse;

import java.util.List;

public interface ICategoryService {
    CategoryResponse getAll();
    void create(CategoryDTO categoryDTO);
    void delete(Long id);
    void update(Long id, CategoryDTO category);
}
