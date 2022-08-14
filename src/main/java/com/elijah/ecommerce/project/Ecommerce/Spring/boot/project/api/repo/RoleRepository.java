package com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.api.repo;

import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.api.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {

    Role findByName(String name);
}
