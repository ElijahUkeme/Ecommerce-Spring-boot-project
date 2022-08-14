package com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.service;

import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.dto.ProductDto;
import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.error.DataNotFoundException;
import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.model.Category;
import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.model.Product;
import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.repository.CategoryRepository;
import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public void addNewProduct(ProductDto productDto,Category category){
        Product product = new Product();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setImageUri(productDto.getImageUri());
        product.setPrice(productDto.getPrice());
        product.setCategory(category);
        productRepository.save(product);
    }
    public ProductDto getProducts(Product product){
        ProductDto productDto = new ProductDto();
        productDto.setDescription(product.getDescription());
        productDto.setCategoryId(product.getCategory().getId());
        productDto.setImageUri(product.getImageUri());
        productDto.setPrice(product.getPrice());
        productDto.setName(product.getName());
        productDto.setId(product.getId());
        return productDto;
    }
    public List<ProductDto> getAllProducts(){
        List<Product> allProducts = productRepository.findAll();
        List<ProductDto> productDtoList = new ArrayList<>();
        for (Product product: allProducts){
            productDtoList.add(getProducts(product));
        }
        return productDtoList;
    }
    public Product updateProduct(ProductDto productDto,Integer productId) throws DataNotFoundException {
        Optional<Product> product = productRepository.findById(productId);

        if (!product.isPresent()){
            throw new DataNotFoundException("Product Id Not Found");
        }
        if (Objects.nonNull(productDto.getName())&& !"".equalsIgnoreCase(productDto.getName())){
            product.get().setName(productDto.getName());
        }
        if (Objects.nonNull(productDto.getDescription())&& !"".equalsIgnoreCase(productDto.getDescription())){
            product.get().setDescription(productDto.getDescription());
        }
        if (Objects.nonNull(productDto.getImageUri())&& !"".equalsIgnoreCase(productDto.getImageUri())){
            product.get().setImageUri(productDto.getImageUri());
        }
        if (Objects.nonNull(productDto.getPrice())){
            product.get().setPrice(productDto.getPrice());
        }
        return productRepository.save(product.get());
    }

    public Product findById(Integer id) throws DataNotFoundException {
        Optional<Product> optionalProduct= productRepository.findById(id);
        if (optionalProduct.isEmpty()){
            throw new DataNotFoundException("The Product Id does not Exist");
        }
        return optionalProduct.get();
    }
}
