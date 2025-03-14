package com.imposto.repository;

import com.imposto.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserModel,Long> {
    boolean existsByUsername(String username);
    Optional<UserModel> findByUsername(String username);
}
