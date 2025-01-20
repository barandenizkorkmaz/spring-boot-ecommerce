package com.org.ecommerce.service;

import com.org.ecommerce.model.Category;
import com.org.ecommerce.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService implements ICategoryService{
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    @Override
    public void create(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public void delete(Long id){
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        Category category = optionalCategory
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        categoryRepository.delete(category);
    }

    @Override
    public void update(Long id, Category newCategory) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        Category category = optionalCategory
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        category.setName(newCategory.getName());
        categoryRepository.save(category);
    }
}
