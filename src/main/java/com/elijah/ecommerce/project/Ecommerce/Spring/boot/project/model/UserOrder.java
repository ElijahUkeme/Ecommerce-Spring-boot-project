package com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.model;

import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.dto.CartDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private @NotNull int totalProduct;
    private @NotNull int userId;

}
