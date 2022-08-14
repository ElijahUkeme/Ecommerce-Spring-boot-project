package com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingDto {

    private List<ShoppingListDto> shoppingListDtos;
    private double totalCost;
}
