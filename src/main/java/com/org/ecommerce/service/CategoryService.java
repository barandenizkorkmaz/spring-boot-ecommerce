package com.org.ecommerce.service;

import com.org.ecommerce.dtos.CategoryDTO;
import com.org.ecommerce.exceptions.APIException;
import com.org.ecommerce.exceptions.ResourceNotFoundException;
import com.org.ecommerce.model.Category;
import com.org.ecommerce.payload.response.CategoryResponse;
import com.org.ecommerce.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
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

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryResponse getAll() {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryDTO> categoryDTOS = categories.stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .toList();
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setContent(categoryDTOS);
        return categoryResponse;
    }

    @Override
    public void create(CategoryDTO categoryDTO) {
        Category existingCategory = categoryRepository.findByName(categoryDTO.getName());
        if(existingCategory != null){
            throw new APIException(String.format("%s with the name %s already exists!", "Category", categoryDTO.getName()));
        }
        Category category = modelMapper.map(categoryDTO, Category.class);
        categoryRepository.save(category);
    }

    @Override
    public void delete(Long id){
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "Id", id));
        categoryRepository.delete(category);
    }

    @Override
    public void update(Long id, CategoryDTO categoryDTO) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "Id", id));
        Category category = modelMapper.map(categoryDTO, Category.class);
        category.setId(id);
        categoryRepository.save(category);
    }
}
