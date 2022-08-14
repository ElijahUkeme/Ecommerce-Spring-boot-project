package com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.dto;

import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.model.Cart;
import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDto {

    private Integer id;
    private Integer quantity;
    private Product product;

    public CartItemDto(Cart cart){
        this.id = cart.getId();
        this.quantity = cart.getQuantity();
        this.setProduct(cart.getProduct());
    }
}
