package com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.service;

import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.dto.AddToCartDto;
import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.dto.CartDto;
import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.dto.CartItemDto;
import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.error.DataNotFoundException;
import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.model.Cart;
import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.model.Product;
import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.model.ShoppingList;
import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.model.User;
import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductService productService;
    @Autowired
    private ShoppingListService shoppingListService;

    public void addToCart(AddToCartDto addToCartDto, User user) throws DataNotFoundException {

        Product product = productService.findById(addToCartDto.getProductId());
        Cart cart = new Cart();
        cart.setCreatedDate(new Date());
        cart.setProduct(product);
        cart.setUser(user);
        cart.setQuantity(addToCartDto.getQuantity());
        cartRepository.save(cart);

    }

    public CartDto itemList(User user) {
        List<Cart> cartList = cartRepository.findAllByUserOrderByCreatedDateDesc(user);
        List<CartItemDto> cartItemDtos = new ArrayList<>();
        double totalCost = 0;
        for (Cart cart : cartList) {
            CartItemDto cartItemDto = new CartItemDto(cart);
            totalCost += cartItemDto.getQuantity() * cart.getProduct().getPrice();
            cartItemDtos.add(cartItemDto);
        }
        CartDto cartDto = new CartDto();
        cartDto.setCartItemDtoList(cartItemDtos);
        cartDto.setTotalCost(totalCost);
        return cartDto;
    }

    public void deleteCartItemByUser(Integer cartItemId, User user) throws DataNotFoundException {
        Optional<Cart> optionalCart = cartRepository.findById(cartItemId);
        if (optionalCart.isEmpty()) {
            throw new DataNotFoundException("Item Id Not Found");
        }
        Cart cart = optionalCart.get();
        if (cart.getUser() != user) {
            throw new DataNotFoundException("The Item doesn't belongs to this user");
        }
        cartRepository.delete(cart);
    }

    public void deleteCartItemByAdmin(Integer cartItemId, User user) throws DataNotFoundException {
        Optional<Cart> optionalCart = cartRepository.findById(cartItemId);
        if (optionalCart.isEmpty()) {
            throw new DataNotFoundException("Item Id Not Found");
        }
        Cart cart = optionalCart.get();
        if (cart.getUser() != user) {
            throw new DataNotFoundException("The Item doesn't belongs to this user");
        }
        Product product = productService.findById(cart.getProduct().getId());
        ShoppingList shoppingList = new ShoppingList();
        shoppingList.setProduct(product);
        shoppingList.setQuantity(cart.getQuantity());
        shoppingList.setDeliveredDate(new Date());
        shoppingList.setUser(cart.getUser());
        shoppingList.setStatus("Delivered");
        //In this case the admin want to delete the item that
        shoppingListService.addToShoppingList(shoppingList);
        cartRepository.delete(cart);
    }

}
