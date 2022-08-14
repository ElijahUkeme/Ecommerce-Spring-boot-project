package com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Please Enter the category Name")
    private String categoryName;
    @NotBlank(message = "Please Enter the Category Description")
    private String description;
    @NotBlank(message = "Please Upload Category Image Url")
    private String imageUri;
}
