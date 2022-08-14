package com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddToCartDto {

    private Integer id;
    private @NotNull Integer productId;
    private @NotNull int quantity;
}
