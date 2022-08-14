package com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.controller;


import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.dto.UserOrderDto;
import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.error.DataAlreadyExistException;
import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.error.DataNotFoundException;
import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.model.User;
import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.model.UserOrder;
import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.response.ApiResponse;
import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.service.AuthenticationTokenService;
import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class UserOrderController {

    @Autowired
    private AuthenticationTokenService authenticationTokenService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private CartController cartController;

    @PostMapping("/place")
    public ResponseEntity<ApiResponse> placeFinalOrder(@RequestParam("token") String token) throws DataNotFoundException, DataAlreadyExistException {
        authenticationTokenService.authenticateToken(token);
        User user = authenticationTokenService.getUserByToken(token);

        if (cartController.isFinalOrderPlacedForThisUser(token)) {
            throw new DataAlreadyExistException("You have already placed a final Order, Please wait for delivery before placing another. Thanks");
        } else {
            orderService.makeFinalOrder(user);
            return new ResponseEntity<>(new ApiResponse(true, "Final Order Placed Successfully"), HttpStatus.CREATED);
        }
    }


    @GetMapping("/list")
    public ResponseEntity<UserOrderDto> getFinalOrderList(@RequestParam("token") String token) throws DataNotFoundException {
        authenticationTokenService.authenticateToken(token);
        User user = authenticationTokenService.getUserByToken(token);
        UserOrderDto userOrderDto = orderService.finalOrderList(user);
        if (userOrderDto.getCartDtos().getCartItemDtoList().size() < 1) {
            throw new DataNotFoundException("This User has not place a final order yet");
        }
        return new ResponseEntity<>(userOrderDto, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{token}")
    public ResponseEntity<ApiResponse> deleteFinalOrder(@RequestParam("token") String token,Integer orderId) throws DataNotFoundException {

        authenticationTokenService.authenticateToken(token);
        User user = authenticationTokenService.getUserByToken(token);
        orderService.deleteFinalOrder(user,orderId);
        return new ResponseEntity<>(new ApiResponse(true, "Final Order Deleted Successfully"), HttpStatus.OK);
    }
}
