package com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private int id;
    @NotBlank(message = "Please Enter the Product Name")
    private String name;
    @NotBlank(message = "Please Enter the Image uri of the Product")
    private String imageUri;
    @NotBlank(message = "Please Enter the price")
    private double price;
    @NotBlank(message = "Please Enter the Product Description")
    private String description;
    @NotBlank
    private Integer categoryId;
}
