package com.org.ecommerce.controller;

import com.org.ecommerce.config.AppConstants;
import com.org.ecommerce.dtos.CategoryDTO;
import com.org.ecommerce.model.Category;
import com.org.ecommerce.payload.response.CategoryResponse;
import com.org.ecommerce.service.ICategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;

    @GetMapping("/public/category")
    //@RequestMapping(value = "/public/category", method = RequestMethod.GET)
    public ResponseEntity<CategoryResponse> getAll(
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE) Integer pageSize
    ){
        CategoryResponse categories = categoryService.getAll(pageNumber, pageSize);
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @PostMapping("/public/category/create")
    public ResponseEntity create(@Valid @RequestBody CategoryDTO category){
        categoryService.create(category);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/category/delete/{categoryId}")
    public ResponseEntity<String> delete(@PathVariable Long categoryId){
        categoryService.delete(categoryId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/admin/category/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody CategoryDTO categoryDTO){
        categoryService.update(id, categoryDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
