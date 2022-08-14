package com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.model;




import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "Please Enter the product Name")
    private String name;
    @NotBlank(message = "Please Enter the Image uri of the Product")
    private String imageUri;
    private double price;
    @NotBlank(message = "Please Enter the Product Description")
    private String description;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "category_id")
    Category category;
}
