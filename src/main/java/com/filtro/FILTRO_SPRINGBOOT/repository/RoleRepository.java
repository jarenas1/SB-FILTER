package com.filtro.FILTRO_SPRINGBOOT.repository;

import com.filtro.FILTRO_SPRINGBOOT.model.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleEntity, String> {
    Optional<RoleEntity> findByName(String name);
}
