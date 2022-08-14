package com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.controller;

import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.dto.ProductDto;
import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.error.DataNotFoundException;
import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.model.Category;
import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.repository.CategoryRepository;
import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.response.ApiResponse;
import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryRepository categoryRepository;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createNewProduct(@RequestBody ProductDto productDto) throws DataNotFoundException {
        Optional<Category> category = categoryRepository.findById(productDto.getCategoryId());
        if (!category.isPresent()){
            return new ResponseEntity<>(new ApiResponse(false,"Category Id Not Found"),HttpStatus.NOT_FOUND);
        }
        productService.addNewProduct(productDto,category.get());
        return new ResponseEntity<>(new ApiResponse(true,"Product Added Successfully"), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProductDto>> getAllProducts(){
        List<ProductDto> allProducts = productService.getAllProducts();
        return new ResponseEntity<>(allProducts,HttpStatus.OK);
    }

    @PutMapping("/update/{productId}")
    public ResponseEntity<ApiResponse> updateProduct(@PathVariable("productId")Integer productId, @RequestBody ProductDto productDto) throws DataNotFoundException {
        productService.updateProduct(productDto,productId);
        return new ResponseEntity<>(new ApiResponse(true,"Product Updated Successfully"),HttpStatus.OK);
    }
}
