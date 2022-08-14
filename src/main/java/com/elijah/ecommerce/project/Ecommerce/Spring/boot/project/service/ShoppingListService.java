package com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.service;

import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.dto.*;
import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.error.DataNotFoundException;
import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.model.Cart;
import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.model.ShoppingList;
import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.model.User;
import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.repository.ShoppingListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ShoppingListService {

    @Autowired
    private ShoppingListRepository shoppingListRepository;


    public void addToShoppingList(ShoppingList shoppingList){
        shoppingListRepository.save(shoppingList);
    }

    public ShoppingDto itemListFoUser(User user){
        List<ShoppingList> shoppingLists = shoppingListRepository.findAllByUserOrderByDeliveredDateDesc(user);
        List<ShoppingListDto> shoppingListDtos = new ArrayList<>();
        double totalCost = 0;
        for (ShoppingList shoppingList: shoppingLists){
            ShoppingListDto shoppingListDto = new ShoppingListDto(shoppingList);
            totalCost = shoppingListDto.getQuantity() * shoppingList.getProduct().getPrice();
            shoppingListDtos.add(shoppingListDto);
        }
        ShoppingDto shoppingDto = new ShoppingDto(shoppingListDtos,totalCost);
        return shoppingDto;
    }
}
