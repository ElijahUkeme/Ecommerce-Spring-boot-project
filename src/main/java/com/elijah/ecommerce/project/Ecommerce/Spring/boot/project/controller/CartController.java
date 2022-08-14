package com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.controller;

import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.dto.AddToCartDto;
import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.dto.CartDto;
import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.dto.UserOrderDto;
import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.error.DataAlreadyExistException;
import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.error.DataNotFoundException;
import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.model.Cart;
import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.model.User;
import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.model.UserOrder;
import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.repository.OrderRepository;
import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.response.ApiResponse;
import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.service.AuthenticationTokenService;
import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.service.CartService;
import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartService cartService;
    @Autowired
    private AuthenticationTokenService authenticationTokenService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addToCart(@RequestParam("token") String token,
                                                 @RequestBody AddToCartDto addToCartDto) throws DataNotFoundException, DataAlreadyExistException {

        authenticationTokenService.authenticateToken(token);
        User user = authenticationTokenService.getUserByToken(token);
        CartDto cartDto = cartService.itemList(user);
        for (int i = 0; i < cartDto.getCartItemDtoList().size(); i++) {
            //loop through the cart items to check if the product
            //the user want to add is already in the cart list
            if (cartDto.getCartItemDtoList().get(i).getProduct().getId() == addToCartDto.getProductId()) {
                throw new DataAlreadyExistException("You have Already Added this product to the Cart");
            }
        }
        if (isFinalOrderPlacedForThisUser(token)) {
            throw new DataAlreadyExistException("You have already placed a final Order, Please wait for delivery before adding order items to your cart. Thanks");
        } else {
            cartService.addToCart(addToCartDto, user);
            return new ResponseEntity<>(new ApiResponse(true, "Product Added to Cart Successfully"), HttpStatus.CREATED);

        }
    }

    //get all the items for that user
    @GetMapping("/items")
    public ResponseEntity<CartDto> getCartItems(@RequestParam("token") String token) throws DataNotFoundException {
        authenticationTokenService.authenticateToken(token);
        User user = authenticationTokenService.getUserByToken(token);
        CartDto cartDto = cartService.itemList(user);
        if (cartDto.getCartItemDtoList().size() < 1) {
            throw new DataNotFoundException("There is no item in this user's Cart yet");
        }
        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }

    //delete item in the cart for that user
    @DeleteMapping("/delete/by/user/{itemId}")
    public ResponseEntity<ApiResponse> deleteItemByUser(@PathVariable("itemId") Integer itemId,
                                                        @RequestParam("token") String token) throws DataNotFoundException {
        authenticationTokenService.authenticateToken(token);
        User user = authenticationTokenService.getUserByToken(token);
        cartService.deleteCartItemByUser(itemId, user);
        return new ResponseEntity<>(new ApiResponse(true, "Item deleted Successfully from the Cart"), HttpStatus.OK);
    }

    //delete item in the cart for that user
    @DeleteMapping("/delete/by/admin/{itemId}")
    public ResponseEntity<ApiResponse> deleteItemByAdmin(@PathVariable("itemId") Integer itemId,
                                                         @RequestParam("token") String token) throws DataNotFoundException {
        authenticationTokenService.authenticateToken(token);
        User user = authenticationTokenService.getUserByToken(token);
        cartService.deleteCartItemByAdmin(itemId, user);
        return new ResponseEntity<>(new ApiResponse(true, "Item deleted Successfully from the Cart"), HttpStatus.OK);
    }

    public boolean isFinalOrderPlacedForThisUser(String token) throws DataNotFoundException {

        boolean isOrderPlaced;
        authenticationTokenService.authenticateToken(token);
        User user = authenticationTokenService.getUserByToken(token);

        if (Objects.isNull(orderRepository.findByUserId(user.getId()))){
            isOrderPlaced = false;
        }else {
            isOrderPlaced = true;
        }
        return isOrderPlaced;
    }
}
