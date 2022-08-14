package com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignInResponseDto {

    private String status;
    private String token;
}
