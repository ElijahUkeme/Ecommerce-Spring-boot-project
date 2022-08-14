package com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignInDto {

    private String email;
    private String password;

}
