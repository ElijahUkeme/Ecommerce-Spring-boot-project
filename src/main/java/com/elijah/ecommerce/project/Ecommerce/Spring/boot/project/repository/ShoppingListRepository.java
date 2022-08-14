package com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.repository;

import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.model.ShoppingList;
import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShoppingListRepository extends JpaRepository<ShoppingList,Integer> {

    List<ShoppingList> findAllByUserOrderByDeliveredDateDesc(User user);
}
