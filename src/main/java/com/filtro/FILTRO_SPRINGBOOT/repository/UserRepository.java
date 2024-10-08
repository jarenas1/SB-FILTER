package com.filtro.FILTRO_SPRINGBOOT.repository;

import com.filtro.FILTRO_SPRINGBOOT.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, String> {
    Optional<UserEntity> findByUsername(String username);
}
