package com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.repository;

import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    User findByEmail(String email);
}
