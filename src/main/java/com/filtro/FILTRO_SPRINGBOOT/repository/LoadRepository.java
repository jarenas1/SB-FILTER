package com.filtro.FILTRO_SPRINGBOOT.repository;

import com.filtro.FILTRO_SPRINGBOOT.model.LoadEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoadRepository extends JpaRepository<LoadEntity, String> {
}
