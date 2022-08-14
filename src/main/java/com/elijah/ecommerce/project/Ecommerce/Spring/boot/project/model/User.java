package com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.model;

import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String profileImage;
    @NotBlank(message = "please Enter your first Name")
    private String firstName;
    @NotBlank(message = "Please Enter your Last Name")
    private String lastName;
    @Email(message = "Please Enter a valid Email Address")
    private String email;
    @NotBlank(message = "Please Enter your Password")
    private String password;
}
