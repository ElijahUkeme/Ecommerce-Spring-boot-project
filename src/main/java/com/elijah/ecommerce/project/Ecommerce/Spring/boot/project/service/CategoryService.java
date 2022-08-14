package com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.service;

import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.dto.ProductDto;
import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.error.DataNotFoundException;
import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.model.Category;
import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.model.Product;
import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public void createCategory(Category category){
        categoryRepository.save(category);
    }

    public List<Category> getCategoryList(){
        return categoryRepository.findAll();
    }

    public Category findById(Integer id) throws DataNotFoundException {
        Optional<Category> category = categoryRepository.findById(id);
        if (!category.isPresent()){
            throw new DataNotFoundException("Category Not Found");
        }
        return category.get();
    }
    public Category updateCategory(Integer categoryId,Category category) throws DataNotFoundException {
        Optional<Category> categoryFromDB = categoryRepository.findById(categoryId);
        if (!categoryFromDB.isPresent()){
            throw new DataNotFoundException("Category Id Not Found");
        }
        if (Objects.nonNull(category.getCategoryName())&& !"".equalsIgnoreCase(category.getCategoryName())){
            categoryFromDB.get().setCategoryName(category.getCategoryName());
        }
        if (Objects.nonNull(category.getDescription())&& !"".equalsIgnoreCase(category.getDescription())){
            categoryFromDB.get().setDescription(category.getDescription());
        }if (Objects.nonNull(category.getImageUri())&& !"".equalsIgnoreCase(category.getImageUri())){
            categoryFromDB.get().setImageUri(category.getImageUri());
        }
        return categoryRepository.save(categoryFromDB.get());
    }
    public void deleteCategory(Integer categoryId) throws DataNotFoundException {
        Optional<Category> category = categoryRepository.findById(categoryId);
        if (!category.isPresent()){
            throw new DataNotFoundException("Category Id Not Found");
        }
        categoryRepository.delete(category.get());
    }

}
