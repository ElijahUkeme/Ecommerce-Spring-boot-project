package com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.dto;


import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.model.UserOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserOrderDto {

    private int id;
    private int totalProduct;
    private int userId;
    private CartDto cartDtos;

    public UserOrderDto (UserOrder userOrder){
        this.totalProduct = userOrder.getTotalProduct();
        this.userId = userOrder.getUserId();
        this.id = userOrder.getId();
    }
}
