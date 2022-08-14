package com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.repository;

import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer> {
}
