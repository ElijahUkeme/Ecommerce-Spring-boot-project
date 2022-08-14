package com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.api.repo;

import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.api.domain.UsersAccess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAccessRepository extends JpaRepository<UsersAccess,Integer> {

    UsersAccess findByUsername(String username);
}
