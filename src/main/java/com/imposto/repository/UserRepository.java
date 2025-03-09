package com.imposto.repository;

import com.imposto.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserModel,Long> {
    boolean existsByUsername(String username);
}
