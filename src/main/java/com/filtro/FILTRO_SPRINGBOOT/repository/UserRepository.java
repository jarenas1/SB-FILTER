package com.filtro.FILTRO_SPRINGBOOT.repository;

import com.filtro.FILTRO_SPRINGBOOT.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, String> {
}
