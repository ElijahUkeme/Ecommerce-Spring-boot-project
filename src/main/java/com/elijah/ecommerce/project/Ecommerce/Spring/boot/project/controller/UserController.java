package com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.controller;

import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.dto.SignInDto;
import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.dto.SignUpDto;
import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.dto.UserDto;
import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.error.DataAlreadyExistException;
import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.error.DataNotFoundException;
import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.response.ApiResponse;
import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.response.SignInResponseDto;
import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;

@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signUp")
    public ResponseEntity<ApiResponse> signUpUser(@RequestBody SignUpDto signUpDto) throws DataAlreadyExistException, DataNotFoundException {
        return userService.signUp(signUpDto);
    }
    @PostMapping("/signIn")
    public SignInResponseDto signIn(@RequestBody SignInDto signInDto) throws DataNotFoundException, NoSuchAlgorithmException {
        return userService.signIn(signInDto);
    }
    @PostMapping("/info")
    public UserDto getUserInfo(@RequestBody SignInDto signInDto) throws NoSuchAlgorithmException, DataNotFoundException {
        return userService.getUserInfo(signInDto);
    }
}
