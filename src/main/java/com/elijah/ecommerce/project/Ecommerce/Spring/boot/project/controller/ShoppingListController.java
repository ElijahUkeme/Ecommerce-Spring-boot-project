package com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.controller;

import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.dto.CartDto;
import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.dto.ProductDto;
import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.dto.ShoppingDto;
import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.error.DataNotFoundException;
import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.model.Product;
import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.model.ShoppingList;
import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.model.User;
import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.response.ApiResponse;
import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.service.AuthenticationTokenService;
import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.service.ShoppingListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shoppingList")
public class ShoppingListController {

    @Autowired
    private ShoppingListService shoppingListService;
    @Autowired
    private AuthenticationTokenService authenticationTokenService;

    @GetMapping("/forOneUser/{token}")
    public ResponseEntity<ShoppingDto> getShoppingList(@PathVariable("token") String token) throws DataNotFoundException {

        authenticationTokenService.authenticateToken(token);
        User user = authenticationTokenService.getUserByToken(token);
        ShoppingDto shoppingDto = shoppingListService.itemListFoUser(user);
        if (shoppingDto.getShoppingListDtos().size()<1){
            throw new DataNotFoundException("This User doesn't have any item in his list yet");
        }
        return new ResponseEntity<>(shoppingDto,HttpStatus.OK);
    }
}
