package com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.repository;

import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.model.UserOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<UserOrder,Integer> {

    UserOrder findByUserId(Integer userId);
}
