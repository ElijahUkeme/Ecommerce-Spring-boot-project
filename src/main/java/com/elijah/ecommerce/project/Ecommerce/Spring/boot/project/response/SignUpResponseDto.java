package com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpResponseDto {

    private String status;
    private String message;
}
