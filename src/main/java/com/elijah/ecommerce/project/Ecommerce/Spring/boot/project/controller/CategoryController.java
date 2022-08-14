package com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.controller;

import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.error.DataNotFoundException;
import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.model.Category;
import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.response.ApiResponse;
import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createCategory(Category category){
        categoryService.createCategory(category);
        return new ResponseEntity<>(new ApiResponse(true,"Category Created Successfully"), HttpStatus.CREATED);
    }
    @GetMapping("/all")
    public List<Category> getAllCategory(){
        return categoryService.getCategoryList();
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable("id") Integer id,@RequestBody Category category) throws DataNotFoundException {
         categoryService.updateCategory(id,category);
         return new ResponseEntity<ApiResponse>(new ApiResponse(true,"Category Updated Successfully"),HttpStatus.OK);
    }
    @GetMapping("/find/{id}")
    public Category findById(@PathVariable("id") Integer id) throws DataNotFoundException {
        return categoryService.findById(id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteCategoryById(@PathVariable("id") Integer id) throws DataNotFoundException {
        categoryService.deleteCategory(id);
        return new ResponseEntity<>(new ApiResponse(true,"Category Deleted Successfully"),HttpStatus.OK);
    }
}
