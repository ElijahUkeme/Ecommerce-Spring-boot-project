package com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ErrorMessage {

    private String message;
    private HttpStatus status;
}
