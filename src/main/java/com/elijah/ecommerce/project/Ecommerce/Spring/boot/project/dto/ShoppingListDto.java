package com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.dto;

import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.model.Product;
import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.model.ShoppingList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingListDto {

    private Integer id;
    private Integer quantity;
    private Product product;
    private String status;

    public ShoppingListDto(ShoppingList shoppingList){
        this.id = shoppingList.getId();
        this.quantity = shoppingList.getQuantity();
        this.product = shoppingList.getProduct();
        this.status = shoppingList.getStatus();
    }
}
