package com.org.ecommerce.service;

import com.org.ecommerce.dtos.CategoryDTO;
import com.org.ecommerce.exceptions.APIException;
import com.org.ecommerce.exceptions.ResourceNotFoundException;
import com.org.ecommerce.model.Category;
import com.org.ecommerce.payload.response.CategoryResponse;
import com.org.ecommerce.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService implements ICategoryService{

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryResponse getAll(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        Page<Category> categoryPage = categoryRepository.findAll(pageDetails);
        List<Category> categories = categoryPage.getContent();
        List<CategoryDTO> categoryDTOS = categories.stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .toList();
        return CategoryResponse.builder()
                .content(categoryDTOS)
                .pageNumber(categoryPage.getNumber())
                .pageSize(categoryPage.getSize())
                .totalElements(categoryPage.getTotalElements())
                .totalNumPages(categoryPage.getTotalPages())
                .lastPage(categoryPage.isLast())
                .build();
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
