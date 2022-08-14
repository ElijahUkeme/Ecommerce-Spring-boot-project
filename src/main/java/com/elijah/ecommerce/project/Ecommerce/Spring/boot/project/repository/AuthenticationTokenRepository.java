package com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.repository;

import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.model.AuthenticationTokens;
import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthenticationTokenRepository extends JpaRepository<AuthenticationTokens,Integer> {
    AuthenticationTokens findByUser(User user);
    AuthenticationTokens findByToken(String token);
}
