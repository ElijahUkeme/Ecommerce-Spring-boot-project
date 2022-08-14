package com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.service;

import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.error.DataNotFoundException;
import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.model.AuthenticationTokens;
import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.model.User;
import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.repository.AuthenticationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AuthenticationTokenService {

    @Autowired
    private AuthenticationTokenRepository authenticationTokenRepository;

    public void saveConfirmationToken(AuthenticationTokens authenticationTokens){
        authenticationTokenRepository.save(authenticationTokens);

    }
    public AuthenticationTokens getToken(User user){
        return authenticationTokenRepository.findByUser(user);
    }
    public User getUserByToken(String token) throws DataNotFoundException {
        AuthenticationTokens authenticationTokens =authenticationTokenRepository.findByToken(token);
        if (Objects.isNull(authenticationTokens)){
            throw new DataNotFoundException("Token is Not Valid");
        }
        return authenticationTokens.getUser();
    }
    public void authenticateToken(String token) throws DataNotFoundException {
        if (Objects.isNull(token)){
            throw new DataNotFoundException("Token not Found");
        }
        if (Objects.isNull(getUserByToken(token))){
            throw new DataNotFoundException("No User with such token");
        }
        getUserByToken(token);
    }
}
