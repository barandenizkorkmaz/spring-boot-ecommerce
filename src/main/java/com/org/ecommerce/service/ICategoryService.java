package com.org.ecommerce.service;

import com.org.ecommerce.dtos.CategoryDTO;
import com.org.ecommerce.model.Category;
import com.org.ecommerce.payload.response.CategoryResponse;

import java.util.List;

public interface ICategoryService {
    CategoryResponse getAll(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);
    void create(CategoryDTO categoryDTO);
    void delete(Long id);
    void update(Long id, CategoryDTO category);
}
