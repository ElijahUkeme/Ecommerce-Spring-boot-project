package com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.service;


import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.dto.CartDto;
import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.dto.CartItemDto;
import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.dto.UserOrderDto;
import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.error.DataNotFoundException;
import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.model.Cart;
import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.model.User;
import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.model.UserOrder;
import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.repository.CartRepository;
import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.repository.OrderRepository;
import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private CartService cartService;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    public void makeFinalOrder(User user) {
        UserOrder userOrder = new UserOrder();
        CartDto cartDto = cartService.itemList(user);

        int totalProducts = 0;
        for (int i = 0; i < cartDto.getCartItemDtoList().size(); i++) {
            totalProducts += cartDto.getCartItemDtoList().get(i).getQuantity();

        }
        userOrder.setTotalProduct(totalProducts);
        userOrder.setUserId(user.getId());
        orderRepository.save(userOrder);
    }

    public UserOrderDto finalOrderList(User user) throws DataNotFoundException {
        UserOrder userOrder = new UserOrder();

        UserOrder orderOptional = orderRepository.findByUserId(user.getId());
        if (Objects.isNull(orderOptional)) {
            throw new DataNotFoundException("Order Id not found for this User");
        }

        userOrder.setUserId(orderOptional.getUserId());
        userOrder.setId(orderOptional.getId());
        userOrder.setTotalProduct(orderOptional.getTotalProduct());
        UserOrderDto userOrderDto = new UserOrderDto(userOrder);
        user.setId(userOrder.getUserId());
        if (userOrder.getUserId() != user.getId()) {
            throw new DataNotFoundException("You are not the owner of this Order");
        }


        if (Objects.isNull(user)) {
            throw new DataNotFoundException("This User has not place final order yet");
        }
        userRepository.findById(user.getId());
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

        userOrderDto.setCartDtos(cartDto);
        return userOrderDto;

    }

    public void deleteFinalOrder(User user, Integer orderId) throws DataNotFoundException {
        Optional<UserOrder> orderOptional = orderRepository.findById(orderId);
        if (!orderOptional.isPresent()) {
            throw new DataNotFoundException("Order Id not found");
        }
         UserOrder userOrder = orderOptional.get();
        if (userOrder.getUserId() != user.getId()) {
            throw new DataNotFoundException("You are trying to delete someone's else final order");
        }
        orderRepository.delete(userOrder);
    }
}
